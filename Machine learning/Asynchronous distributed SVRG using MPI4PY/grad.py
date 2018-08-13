import asyncoro, random
import numpy as np
import theano
import theano.tensor as T
from scipy.sparse import lil_matrix



def generate_dataset(n):
    d = 10

    # randomly generate training data
    A = np.random.randn(n, d)
    x_true = np.random.randn(d)
    y = np.sign(np.dot(A, x_true) + 0.1 * np.random.randn(n))
    A_test = np.random.randn(n, d)
    y_test = np.sign(np.dot(A_test, x_true))

        # preprocess data
    tmp = lil_matrix((n, n))
    tmp.setdiag(y)
    data = theano.shared(tmp * A)


def server_proc(coro=None):
    coro.set_daemon()

    n = 1000
    d =10

    generate_dataset(1000)
    
    step_size = 0.001

    l2 = 1e-2
    par = T.vector()
    loss = T.log(1 + T.exp(-T.dot(theano.shared(tmp * A), par))).mean() + l2 / 2 * (par ** 2).sum()
    func = theano.function(inputs=[par], outputs=loss)

    idx = T.ivector()
    grad = theano.function(inputs=[par, idx], outputs=T.grad(loss, wrt=par),
                               givens={data: data[idx, :]})


    x0 = np.random.rand(d)

    if x0 is None:
        x = np.zeros(d)
    elif isinstance(x0, np.ndarray) and x0.shape == (d, ):
        x = x0.copy()
    else:
        raise ValueError('x0 must be a numpy array of size (d, )')


        #Start for loop for sending x_tilde



    #yield coro.send(x_tilde)

    for t in range(1):

        for i in range(0, 500, 125):
            yield coro.send(i)
            print('sent index values')


            #print('sent')

    for k in range(100):
        x_tilde = x.copy()

        yield coro.send(x_tilde)
            #print('sent')

        f_g = yield coro.receive()
        f_g +=f_g
        print('%s' % f_g)

msg_id = 0

def client_proc(server, n, coro=None):
    global msg_id
    a = yield coro.receive()

    generate_dataset(a)


    for i in range(250):
        full_grad = grad(x_tilde, range(n))

    server.send('%s' %(full_grad))


server = asyncoro.Coro(server_proc)
for i in range(4):
    asyncoro.Coro(client_proc, server, i)
