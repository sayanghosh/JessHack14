# -*- coding: utf-8 -*-
"""
Created on Sat Nov 08 10:56:06 2014

@author: Sayan
"""
import os
from scipy.io.wavfile import read
from math import log
import numpy as np
from matplotlib import pyplot as plt
import sys

class DiscretizedList:
    def __init__(self,input_list):
        self.input_list=np.array(input_list)
        self.min=np.percentile(self.input_list,0)
        self.lower_quartile=np.percentile(self.input_list,25)
        self.median=np.percentile(self.input_list,50)
        self.upper_quartile=np.percentile(self.input_list,75)
        self.maximum=np.percentile(self.input_list,100)
        #print self.min, self.lower_quartile, self.median, self.upper_quartile, self.maximum
        
    def discretize_3level(self):
        class_0=self.input_list>=self.min
        class_1=self.input_list>=self.lower_quartile
        class_2=self.input_list>=self.median
        class_3=self.input_list>=self.upper_quartile
        decision=(class_0).astype(int)+class_1+class_2+class_3
        return (decision-1).tolist()
    def discretize_2level(self):
        #print self.input_list
        class_0=(self.input_list>=0.25)
        #print class_0        
        return class_0.astype(int).tolist() 
        
class SongToFeatures:
    def __init__(self,wave_file,refresh_interval,output_list_file):
        self.wave_file=wave_file
        self.song_pitch=[]
        self.song_track=[]
        self.sound_loudness=[]
        self.time_list=[]
        self.Fs=None
        self.refresh_interval=refresh_interval
        self.output_list_file=output_list_file
        
    def extract_pitch(self):
        # Perform system call
        os.system('..\\audiolib\\aubiopitch.exe -i '+self.wave_file+' -p default > ..//data//PitchFile.txt')
        # Parse the output file to get the lists
        time_list=[]
        pitch_list=[]
        with open("..//data//PitchFile.txt","rt") as file_handle:
            for row in file_handle:
                rsplit=row.split(" ")
                time_list.append(float(rsplit[0]))
                pitch_list.append(float(rsplit[1][:-2]))
        os.system('del ..\\data\\PitchFile.txt')
        return (time_list,pitch_list)        
    def extract_beats(self):
        # Perform system call
        os.system('..\\audiolib\\aubiotrack.exe -i '+self.wave_file+' > ..//data//TrackFile.txt')
        time_list=[]
        data_list=[]
        with open("..//data//TrackFile.txt","rt") as file_handle:
            for row in file_handle:
                time_list.append(float(row[:-2]))
                data_list.append(1.0)
        os.system('del ..\\data\\TrackFile.txt')
        return (time_list,data_list)        
    def extract_intensity(self):
        x=read(self.wave_file)
        self.Fs=x[0]
        data=(x[1][:,0]+x[1][:,1])/2
        time_list=[]
        loud_list=[]
        for t in range(0,data.shape[0],int(self.Fs*0.25)):
            time_list.append(float(t)/self.Fs)
            loud_list.append(log(0.0000001+abs(data[t])))
        #print time_list, loud_list    
        return (time_list,loud_list)
    def map_time_window(self,time_list,data_list,mode):
        map_time_list={}
        for t in range(0,int(time_list[-1]/self.refresh_interval)+1):
            map_time_list[t]=[0]
        cnt=0
        for time_item in time_list:
            # Find which time slot it belongs to
            time_key=int(time_item/self.refresh_interval)
            map_time_list[time_key].append(data_list[cnt])
            cnt+=1
        for time_item in map_time_list.keys():
            if mode=='average':
                map_time_list[time_item]=np.mean(map_time_list[time_item])
            elif mode=="min":
                map_time_list[time_item]=np.min(map_time_list[time_item])
            else:
                map_time_list[time_item]=np.max(map_time_list[time_item])
        return map_time_list 
    def process_pitch_beats_intensity(self,pitch_map,beats_map,intensity_map):
        # Process the pitch map
        pitch_discretized_list=DiscretizedList(pitch_map.values()).discretize_3level()
        beats_discretized_list=DiscretizedList(beats_map.values()).discretize_2level()
        intensity_discretized_list=DiscretizedList(intensity_map.values()).discretize_3level()
        return pitch_discretized_list,beats_discretized_list,intensity_discretized_list        
    def write_lists(self,pitch_discretized_list,beats_discretized_list,intensity_discretized_list):
        ln=np.min((len(pitch_discretized_list),len(beats_discretized_list),len(intensity_discretized_list)))
        with open(self.output_list_file,"wt") as file_handle:
            for t in range(0,ln):
                file_handle.write(str(pitch_discretized_list[t])+str(beats_discretized_list[t])+str(intensity_discretized_list[t])+"\n")                     
            
def main():
    # sys.argv will contain 
    song_object=SongToFeatures(sys.argv[1],float(sys.argv[2]),sys.argv[3])
    pitch_time_list, pitch_data_list = song_object.extract_pitch()
    intensity_time_list, intensity_data_list = song_object.extract_intensity()
    beats_time_list, beats_data_list = song_object.extract_beats()
    
    pitch_map=song_object.map_time_window(pitch_time_list,pitch_data_list,"average")
    intensity_map=song_object.map_time_window(intensity_time_list,intensity_data_list,"average")
    beats_map=song_object.map_time_window(beats_time_list,beats_data_list,"average")
    #print intensity_map
    (pitch_discretized_list,beats_discretized_list,intensity_discretized_list)=song_object.process_pitch_beats_intensity(pitch_map,beats_map,intensity_map)
    song_object.write_lists(pitch_discretized_list,beats_discretized_list,intensity_discretized_list)
    #plt.plot(intensity_discretized_list)
    #plt.show()
    
if __name__=='__main__':
    main()        