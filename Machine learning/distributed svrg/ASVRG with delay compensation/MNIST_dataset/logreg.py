from __future__ import print_function

__docformat__ = 'restructedtext en'
from scipy.sparse import lil_matrix
import theano
from theano import tensor as T
import numpy as np
from load import mnist
from svrg import svrg1

def floatX(X):
    return np.asarray(X, dtype=theano.config.floatX)

def init_weights(shape):
    return (floatX(np.random.randn(*shape) * 0.01))

def model(X, w):
    return T.nnet.softmax(T.dot(X, w))

trX, teX, trY, teY = mnist(onehot=True)
A = trX
print(A.shape)
y=trY
print(y.shape)
n = 60000
tmp = lil_matrix((n,n))
#tmp.setdiag(y)

data = theano.shared(tmp * A)
X= T.matrix()

Y= T.ivector()
w= init_weights((784,10))
py_x = model(X, w)
y_pred = T.argmax(py_x, axis=1)
cost = T.mean(T.nnet.categorical_crossentropy(py_x, Y))

idx = T.ivector()

func = theano.function(inputs= [X,Y], outputs = cost)
batch_size=128

grad = theano.function(inputs=[w, idx], outputs=T.grad(cost, wrt=w),
                          givens={X:trX[2 * batch_size: (2 + 1) * batch_size]})
w0= init_weights((784,10))
w=svrg1(grad, 1e-3, func=func, max_epoch=50)
