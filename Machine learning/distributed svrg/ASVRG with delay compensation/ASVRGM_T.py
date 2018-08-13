## Sending from server to worker
from mpi4py import MPI

import numpy as np
import theano
import theano.tensor as T
from scipy.sparse import lil_matrix
import random
import matplotlib.pyplot as plt
comm = MPI.COMM_WORLD
rank = comm.Get_rank()
size = comm.Get_size()
name = MPI.Get_processor_name ()
n, d = 1000, 100

num_workers = 4
partition = n/num_workers

A = np.random.randn(n, d)
x_true = np.random.randn(d)

y = np.sign(np.dot(A, x_true) + 0.1 * np.random.randn(n))

#TESTING DATA
A_test = np.random.randn(n, d)
y_test = np.sign(np.dot(A_test, x_true))

#PREPROCESS DATA
tmp = lil_matrix((n, n))
tmp.setdiag(y)
data = theano.shared(tmp * A)

l2 = 1e-2
par = T.vector()
loss = T.log(1 + T.exp(-T.dot(data, par))).mean() + l2 / 2 * (par ** 2).sum()
func = theano.function(inputs=[par], outputs=loss)

idx = T.ivector()
grad = theano.function(inputs=[par, idx], outputs=T.grad(loss, wrt=par),
                           givens={data: data[idx, :]})
num_workers = 2
verbose = True
idx = (random.randrange(n), )
x0 = np.random.rand(d)
process = 1
v = np.random.rand(d)
die = np.zeros(d)
die1 = -1
if rank == 0 :
    print("this is master loop")

        #print (size)
    y = np.zeros(d)
    mom=0.8
    if x0 is None:
        x = np.zeros(d)
    elif isinstance(x0, np.ndarray) and x0.shape == (d, ):
        x = x0.copy()
    else:
        raise ValueError('x0 must be a numpy array of size (d, )')

    step_size = 0.001
    
    for k in range(135):
        process = 1
        x_tilde = x.copy()
        msg1 = x_tilde
        msg2 = x
    # Send the first batch of processes to the nodes.
        while process < size:
            comm.isend(msg1, dest=process, tag=1)
#            print("send x_tilde to worker")
            process += 1

        # Send the shutdown signal
        for process in range(1,size):
            comm.isend(die, dest=process, tag=1)


        fg = grad(x_tilde, range(n))

        last_full_grad = fg
        last_x_tilde = x_tilde


        for m in range(135):
            process = 1
       

            while process < size:
                comm.isend(msg2, dest=process, tag=101)
                comm.isend(m,dest=process,tag = 103)
 #               
                process += 1
 
            for process in range(1,size):
                comm.isend(die, dest=process, tag=101)
                comm.isend(m, dest=process, tag=103)

            if size > 1:
                for i in range (1, size):
  #                  
                    rank, size, name, sg1 = MPI.COMM_WORLD.recv (source=MPI.ANY_SOURCE, tag=22)
                    rank, size, name, sg2 = MPI.COMM_WORLD.recv (source=MPI.ANY_SOURCE, tag=202)
                    rank, size, name, ind = MPI.COMM_WORLD.recv (source=MPI.ANY_SOURCE, tag=302)
                    

            if ind != m :

                delay = m - ind
                print(delay)
                mom = mom / (delay+1)
#            print(mom)

            u = (sg2 - sg1 +fg)
            v= v - step_size * (u)
            x = x_tilde + mom * ( v - x_tilde ) 


        if verbose:
           # output = 'Epoch.: %d' % (k)
            #if func is not None:
             #   output += ', Func. value: %e' % func(x)
            print(func(x))




    y_predict = np.sign(np.dot(A_test, x))
    print('Test accuracy: %f' % (np.count_nonzero(y_test == y_predict)*1.0 / n))

else:
    print("this is worker loop ")
    for p in range(135):

        while True:
            par1 = comm.recv(source=0, tag=1)

            if par1.any()==0 : break
            s1 = grad(par1, idx)


        for q in range(135):
            while True:
                par2 = comm.recv(source=0, tag=101)
                index = comm.recv(source=0, tag=103)

                if par2.any()==0 : break
                s2 = grad(par2, idx)


            MPI.COMM_WORLD.isend ((rank, size, name, s1), dest=0, tag=22)
            MPI.COMM_WORLD.isend ((rank, size, name, s2), dest=0, tag=202)
            MPI.COMM_WORLD.isend ((rank, size, name, index), dest=0, tag=302)
