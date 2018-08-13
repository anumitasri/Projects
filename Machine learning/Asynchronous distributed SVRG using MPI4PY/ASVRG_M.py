import asyncoro, random
import numpy as np
import theano
import theano.tensor as T
from scipy.sparse import lil_matrix



def full_gradient(coro=None):

    result = 0
    while True:
        msg = yield coro.receive()
        print('recieved')
        if msg is None:
            break

        result += msg
        final = result/4

    channel = asyncoro.Channel('gradient_calc')
    server_coro = asyncoro.Coro(server_proc)

    yield channel.subscribe(server_coro)


    channel.send(final)

    channel.send(None)


    yield channel.unsubscribe(server_coro)


def server_proc(coro=None):
    coro.set_daemon()
    while True:

        n, d = 1000, 10
        step_size = 0.001

        # randomly generate training data
        A = np.random.randn(n, d)
        x_true = np.random.randn(d)
        y = np.sign(np.dot(A, x_true) + 0.1 * np.random.randn(n))

        # generate test data

        A_test = np.random.randn(n, d)
        y_test = np.sign(np.dot(A_test, x_true))

        # preprocess data
        tmp = lil_matrix((n, n))
        tmp.setdiag(y)
        data = theano.shared(tmp * A)

        # define objective function and gradient
        l2 = 1e-2
        par = T.vector()
        loss = T.log(1 + T.exp(-T.dot(data, par))).mean() + l2 / 2 * (par ** 2).sum()
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

        a = np.zeros(d)
        #Start for loop for sending x_tilde

        for k in range(1000):
            x_tilde = x.copy()
            # send x_tilde to client to find full gradient
            yield coro.send(x_tilde)
            #print('sent')

            f_g = yield coro.receive()

            f_g +=f_g

            ast_full_grad = f_g
            last_x_tilde = x_tilde


            output = '' % \
                     ()
            if func is not None:

                output += ' %e' % func(x)
            print(output)


            for i in range(100):
                yield coro.send(x_tilde)


                s_gd = yield coro.receive()
                #print('SGD is %s' %s_gd)

                #print('SVRG step')
                idx = (random.randrange(n), )
                a -= step_size * (grad(x, idx) - s_gd + f_g)

                x = x_tilde + 0.9*(a - x_tilde) 

        return x


msg_id = 0

def client_proc(server, n, coro=None):
    global msg_id
    a = yield coro.receive()
    full_grad = grad(x_tilde, range(n))

    for x in range(3):
        yield coro.suspend(random.uniform(0.5, 3))
        msg_id += 1
        server.send('%s' %(full_grad))

    b = yield coro.receive()
    s_gd = grad(x_tilde, idx)

    for x in range(3):
        yield coro.suspend(random.uniform(0.5, 3))
        msg_id += 1
        print('%s' %sgd)
server = asyncoro.Coro(server_proc)
for i in range(4):
    asyncoro.Coro(client_proc, server, i)


