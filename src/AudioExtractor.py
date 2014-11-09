# -*- coding: utf-8 -*-
"""
Created on Sat Nov 08 01:03:48 2014

@author: Eugene
"""
import matplotlib.pyplot as plt

file_name="C:\Users\Eugene\Documents\Coding\Hackathon14\data\sorrowPitch.txt"
time_list=[]
pitch_list=[]

with open(file_name,"rt") as file_handle:
    for row in file_handle:
        rsplit=row.split(" ")
        time_list.append(float(rsplit[0]))
        pitch_list.append(float(rsplit[1][:-2]))
#print time_list
print pitch_list
plt.plot(time_list,pitch_list)
plt.show()

