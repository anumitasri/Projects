import numpy as np
import random



def svrg(grad, init_step_size, n, d, max_epoch=100, m=0, x0=None, func=None,
            verbose=True):

    if not isinstance(m, int) or m <= 0:
        m = n
        if verbose:
            print('Info: set m=n by default')

    if x0 is None:
        x = np.zeros(d)
    elif isinstance(x0, np.ndarray) and x0.shape == (d, ):
        x = x0.copy()
    else:
        raise ValueError('x0 must be a numpy array of size (d, )')

    step_size = init_step_size
    for k in range(max_epoch):
        full_grad = grad(x, range(n))
        x_tilde = x.copy()

        last_full_grad = full_grad
        last_x_tilde = x_tilde
        if verbose:
            output = (func(x))
            #if func is not None:
             #   output += ', Func. value: %e' % func(x)
            print(output)

        for i in range(m):
            idx = (random.randrange(n), )
            x -= step_size * (grad(x, idx) - grad(x_tilde, idx) + full_grad)

    return x
def sgd(grad, init_step_size, n, d, max_epoch=100, m=0, x0=None, beta=0, phi=lambda k: k,
           func=None, verbose=True):

    if not isinstance(m, int) or m <= 0:
        m = n
        if verbose:
            print('Info: set m=n by default')

    if beta <= 0 or beta >= 1:
        beta = 10/m
        if verbose:
            print('Info: set beta=10/m by default')

    if x0 is None:
        x = np.zeros(d)
    elif isinstance(x0, np.ndarray) and x0.shape == (d, ):
        x = x0.copy()
    else:
        raise ValueError('x0 must be a numpy array of size (d, )')

    step_size = init_step_size
    c = 1
    for k in range(max_epoch):
        x_tilde = x.copy()
        if verbose:
            full_grad = grad(x, range(n))
            output = 'Epoch.: %d, Grad. norm: %.2e' % \
                     (k, np.linalg.norm(full_grad))
            if func is not None:
                output += ', Func. value: %e' % func(x)
            print(output)
        if k > 0:
            last_grad_hat = grad_hat
            last_x_tilde = x_tilde
        grad_hat = np.zeros(d)
        for i in range(m):
            idx = (random.randrange(n), )
            g = grad(x, idx)
            x -= step_size * g
            # average the gradients
            grad_hat = beta*g + (1-beta)*grad_hat

    return x

def svrg_mom(grad, init_step_size, n, d, max_epoch=100, m=0, x0=None, func=None,
            verbose=True):

    if not isinstance(m, int) or m <= 0:
        m = n
        if verbose:
            print('Info: set m=n by default')

    if x0 is None:
        x = np.zeros(d)
    elif isinstance(x0, np.ndarray) and x0.shape == (d, ):
        x = x0.copy()
    else:
        raise ValueError('x0 must be a numpy array of size (d, )')

    step_size = init_step_size
    for k in range(max_epoch):
        full_grad = grad(x, range(n))
        x_tilde = x.copy()

        last_full_grad = full_grad
        last_x_tilde = x_tilde
        y = last_x_tilde
        if verbose:
            output = (func(x))
            #if func is not None:
               # output += ', Func. value: %e' % func(x)
            print(output)
        for i in range(m):
            idx = (random.randrange(n), )
            grad_tilde = (grad(x, idx) - grad(x_tilde, idx) + full_grad)
            y -= step_size * grad_tilde
            x = x_tilde + (0.9 * y - (x_tilde))
    return x

def sarah(grad, init_step_size, n, d, max_epoch=100, m=0, x0=None, func=None,
            verbose=True):

    if not isinstance(m, int) or m <= 0:
        m = n
        if verbose:
            print('Info: set m=n by default')

    if x0 is None:
        x = np.zeros(d)
    elif isinstance(x0, np.ndarray) and x0.shape == (d, ):
        x = x0.copy()
    else:
        raise ValueError('x0 must be a numpy array of size (d, )')

    step_size = init_step_size
    for k in range(max_epoch):
        full_grad = grad(x, range(n))
        x_hat = x.copy()
        x = x_hat - step_size * full_grad
        last_full_grad = full_grad

        if verbose:
            output = 'Epoch.: %d, Grad. norm: %.2e' % \
                     (k, np.linalg.norm(full_grad))
            if func is not None:
                output += ', Func. value: %e' % func(x)
            print(output)

        for i in range(m):
            idx = (random.randrange(n), )
            x -= step_size * (grad(x, idx) - grad(x_hat, idx) + full_grad)
    return x

def sarah_mom(grad, init_step_size, n, d, max_epoch=100, m=0, x0=None, func=None,
            verbose=True):

    if not isinstance(m, int) or m <= 0:
        m = n
        if verbose:
            print('Info: set m=n by default')

    if x0 is None:
        x = np.zeros(d)
    elif isinstance(x0, np.ndarray) and x0.shape == (d, ):
        x = x0.copy()
    else:
        raise ValueError('x0 must be a numpy array of size (d, )')

    step_size = init_step_size
    for k in range(max_epoch):
        full_grad = grad(x, range(n))
        x_hat = x.copy()
        x = y = last_x_tilde 
        last_full_grad = full_grad

        if verbose:
            output = 'Epoch.: %d, Grad. norm: %.2e' % \
                     (k, np.linalg.norm(full_grad))
            if func is not None:
                output += ', Func. value: %e' % func(x)
            print(output)

        for i in range(m):
            idx = (random.randrange(n), )
            x = x_tilde + 0.9 * (step_size * (grad(x, idx) - grad(x_hat, idx) + full_grad)- x_tilde)
            
    return x

def sgd_mom(grad, init_step_size, n, d, max_epoch=100, m=0, x0=None, beta=0, phi=lambda k: k,
           func=None, verbose=True):

    if not isinstance(m, int) or m <= 0:
        m = n
        if verbose:
            print('Info: set m=n by default')

    #if beta <= 0 or beta >= 1:
        #beta = 10/m
        #if verbose:
            #print('Info: set beta=10/m by default')

    if x0 is None:
        x = np.zeros(d)
    elif isinstance(x0, np.ndarray) and x0.shape == (d, ):
        x = x0.copy()
    else:
        raise ValueError('x0 must be a numpy array of size (d, )')

    step_size = init_step_size
    v = np.zeros(d)
    for k in range(max_epoch):
        x_tilde = x.copy()

        if verbose:
            full_grad = grad(x, range(n))
            output = 'Epoch.: %d, Grad. norm: %.2e' % \
                     (k, np.linalg.norm(full_grad))
            if func is not None:
                output += ', Func. value: %e' % func(x)
            print(output)

        if k > 0:
            grad_hat = np.zeros(d)   #v = x_tilde
        #x_tilde = v
        #grad_hat = np.zeros(d)
        for i in range(m):
            idx = (random.randrange(n), )
            g = grad(x, idx)
            x = x - (0.9 * v + step_size * g)
            #x = x - v

    return x
