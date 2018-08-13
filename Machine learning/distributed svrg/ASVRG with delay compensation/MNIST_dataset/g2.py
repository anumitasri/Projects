import theano
from theano import tensor as T
import numpy as np
from load1 import mnist1

def floatX(X):
    return np.asarray(X, dtype=theano.config.floatX)

def init_weights(shape):
    return theano.shared(floatX(np.random.randn(*shape) * 0.01))

def model(X, w):
    return T.nnet.softmax(T.dot(X, w))

trX, teX, trY, teY = mnist1(onehot=True)


def gradient2(w):
	X = T.fmatrix()
	Y= T.fmatrix()
	py_x = model(X, w)
	y_pred =T.argmax(py_x, axis=1)
	cost = T.mean(T.nnet.categorical_crossentropy(py_x, Y))
	gradient=T.grad(cost=cost, wrt=w)
	return gradient


