import numpy as np
import pandas as pd
import matplotlib.pyplot as plt

a = np.array((2, 3, 4))
s = pd.Series([1, 3, 5, np.nan, 6, 8])
dates = pd.date_range("20130101", periods=6)
df = pd.DataFrame(np.random.randn(6, 4), index=dates, columns=list("ABCD"))
# print("a", a)
# print("s", s[1])
# print("df", df)
# print("dates", dates)

