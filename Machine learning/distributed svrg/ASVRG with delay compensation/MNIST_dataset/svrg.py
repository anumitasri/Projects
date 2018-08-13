import numpy as np
import random


def svrg1(grad, init_step_size, w0, func=None,verbose=True):
    
  
    
    w= w0.copy 
    n=60000

    step_size = init_step_size
    for k in range(10):
        full_grad = grad(w, range(n))
        w_tilde = w.copy()
      
        if verbose:
            output = 'Epoch.: %d, Step size: %.2e, Grad. norm: %.2e' % \
                     (k, step_size, np.linalg.norm(full_grad))
            if func is not None:
                output += ', Func. value: %e' % func(w)
            print(output)

        for i in range(m):
            idx = (random.randrange(n),)
            w-= step_size * (grad(w, idx) - grad(w_tilde, idx) + full_grad)

    return w
