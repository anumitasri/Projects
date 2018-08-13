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
        

        for i in range(10):
        	process =1


        	while process < size:
        		comm.send(w, dest=process, tag=1)
        		print("sent w  to worker",process)
        		process+=1
        	for process in range(1,size):
        		comm.send(-1,dest=process,tag=1)
        		print("sent die signal")

else:
        while True:
                start = comm.recv(source=0, tag=1)
                print("worker got index")

                if start == -1 : break
                w_tilde=comm.recv(source=0,tag=1)
                if w_tilde==-1:break

                      
