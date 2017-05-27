import numpy as np
import cv2
import os
import copy

def remove_background(imagePath):
	#cv2.namedWindow('image', cv2.WINDOW_NORMAL)
	#print("Load the Image")
	imgo = cv2.imread(imagePath)
	height, width = imgo.shape[:2]
	 
	#print("Create a mask holder")
	mask = np.zeros(imgo.shape[:2],np.uint8)
	 
	#print("Grab Cut the object")
	bgdModel = np.zeros((1,65),np.float64)
	fgdModel = np.zeros((1,65),np.float64)
	 
	#print("Hard Coding the Rectangle, The object must lie within this rect.")
	rect = (10,10,width-30,height-30)
	cv2.grabCut(imgo,mask,rect,bgdModel,fgdModel,5,cv2.GC_INIT_WITH_RECT)

	#print("grabCut")
	mask = np.where((mask==2)|(mask==0),0,1).astype('uint8')
	img1 = imgo*mask[:,:,np.newaxis]
	 
	#print("Get the background")
	background = imgo - img1

	#print("Change all pixels in the background that are not black to white")
	background[np.where((background > [0,0,0]).all(axis = 2))] = [255,255,255]
	 
	#print("Add the background and the image")
	final = background + img1

	
	# cv2.imshow('image', final) # debug statement	 
	newPath = ('.').join(imagePath.split('.')[:-1])
	cv2.imwrite(newPath + '-editted' + '.jpg',final)
	# k = cv2.waitKey(0)	# debug statement 
	# if k==27: # debug statement
	#     cv2.destroyAllWindows() # debug statement


def images_iterator(imgsPath,finalPath):
	imgs = os.listdir(imgsPath)
	for i in range (0, len(imgs)):
		remove_background(imgsPath + '/' + imgs[i])
		newimgName = ('.').join(imgs[i].split('.')[:-1])
		print (imgsPath + newimgName+ '-editted' + '.jpg') # debug statement
		os.rename(imgsPath + newimgName+ '-editted' + '.jpg', finalPath + imgs[i])#+ '-editted' + '.jpg')

def main():
	path = '/media/sf_ubuntu-sf/outfitted/other/'
	oldtopsDir = path + 'oldTops/' 
	oldbotsDir = path + 'oldBottoms/'
	topsDir = path + 'Tops/'
	botsDir = path + 'Bottoms/'
	images_iterator(oldtopsDir,topsDir)
	images_iterator(oldbotsDir,botsDir)
	
if __name__ == '__main__':
	main()
