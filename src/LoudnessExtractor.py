from scipy.io.wavfile import read
from matplotlib import pyplot as plt
from math import log

file_name = 'C:\Users\Eugene\Documents\Coding\Hackathon14\data\sorrow.wav'

x=read(file_name)
Fs=x[0]
data=(x[1][:,0]+x[1][:,1])/2
time_list=[]
loud_list=[]
print Fs
for t in range(0,data.shape[0],int(Fs*0.25)):
    time_list.append(t/Fs)
    loud_list.append(log(0.0000001+abs(data[t])))
#datalog=log(abs(data))
plt.plot(time_list,loud_list)
plt.show()


