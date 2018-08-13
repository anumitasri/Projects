import numpy as np
import random



def svrg_bb(grad, init_step_size, n, d, max_epoch=100, m=0, x0=None, func=None,
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
            output = 'Epoch.: %d, Step size: %.2e, Grad. norm: %.2e' % \
                     (k, step_size, np.linalg.norm(full_grad))
            if func is not None:
                output += ', Func. value: %e' % func(x)
            print(output)

        for i in range(m):
            idx = (random.randrange(n), )
            grad_tilde = (grad(x, idx) - grad(x_tilde, idx) + full_grad)
            y -= step_size * grad_tilde
            x = x_tilde + (0.9 * y - (x_tilde))
    return x



