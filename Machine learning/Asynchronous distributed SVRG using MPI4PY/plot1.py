import matplotlib.pyplot as plt

from sklearn.datasets import make_classification

plt.figure(figsize=(5,5))



X1, Y1 = make_classification(n_samples=500, n_features=10, n_informative=2, n_repeated=0, n_classes=2, weights=None, shuffle=True, random_state=None)


plt.scatter(X1[:, 0], X1[:, 1], marker='o', c=Y1,
            s=25, edgecolor='k')
plt.show()

