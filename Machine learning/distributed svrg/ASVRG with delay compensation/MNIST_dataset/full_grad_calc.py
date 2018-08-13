from mpi4py import MPI
import time
import theano
import theano.tensor as T
from load import mnist
import numpy as np
comm = MPI.COMM_WORLD
rank = comm.Get_rank()
size = comm.Get_size()
name = MPI.Get_processor_name ()
from g1 import gradient1
from g2 import gradient2

if rank == 0:

        process = 1
        n = 60000
        num_workers = 2
        i1 = 30000
        i2=30001
        max_epochs =100


        if process == 1:

            comm.isend(i1, dest=process, tag=1)
            print("Sending index ",i1, "from server to worker",process)
            process +=1

        if process == 2:
           
            comm.isend(i2, dest=process, tag=1)
            print("Sending index ",i2, "from server to worker",process)
            process +=1

        for process in range(1,size):
            comm.isend(-1, dest=process, tag=1)


        def floatX(X):
        	return np.asarray(X, dtype=theano.config.floatX)

        def init_weights(shape):
        	return theano.shared(floatX(np.random.randn(*shape) * 0.01))

        def model(X,w):
        	return T.nnet.softmax(T.dot(X, w))


        trX, teX, trY, teY = mnist(onehot=True)
        X = T.fmatrix()
        Y = T.fmatrix()
        w = init_weights((784, 10))

        w0 = theano.shared(value=np.zeros((784, 10),dtype=theano.config.floatX))
        proc =1


        w_tilde=w.copy()
                #broadcast w_tilde
        while proc<size:
                comm.isend(w_tilde, dest=proc, tag=2)
                proc+=1
        for proc in range(1,size):
                comm.isend(-1, dest=proc, tag=2)


        if size>1:
        	i = 1
        	if i ==1:
        		print("receiving for fg1 ")
        		rank,size, name, fg1 = MPI.COMM_WORLD.recv (source=MPI.ANY_SOURCE, tag=11)
        		i+=1
  
        	if i ==2:
        		print("receiving for fg2 ")
        		rank,size, name, fg2 = MPI.COMM_WORLD.recv (source=MPI.ANY_SOURCE, tag=22)

       	full_grad = (fg1+fg2)/2
       	print("found full grad")

else:
        while True:
            start = comm.recv(source=0, tag=1)

            if start == -1 : break
            w_til= comm.recv(source=0, tag=2)

            if w_til == -1: break

            if start ==30000:
            
            	full_gradient = gradient1(w_til)
            	print("calculated fg")
            	MPI.COMM_WORLD.isend ((rank, size, name, full_gradient), dest=0, tag=11)
            	print("sent from worker1")
            	

            if start == 30001:
            	
            	full_gradient= gradient2(w_til)
            	print("calculated 2nd grad")
            	MPI.COMM_WORLD.isend ((rank, size, name, full_gradient), dest=0, tag=22)
            	print("sent from worker 2")
            	
            	






