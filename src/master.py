import numpy as np
import cv2
import os

def remove_background(imagePath):
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

	print("Add the background and the image")
	final = background + img1
	# gray = cv2.cvtColor(img1,cv2.COLOR_BGR2GRAY)
	# _,thresh = cv2.threshold(gray,1,255,cv2.THRESH_BINARY)
	# _,contours,hierarchy = cv2.findContours(thresh,cv2.RETR_EXTERNAL,cv2.CHAIN_APPROX_SIMPLE)
	# x,y,w,h = cv2.boundingRect(contours[0])
	# crop = final[y:y+h,x:x+w]

	newPath = ('.').join(imagePath.split('.')[:-1])
	cv2.imwrite(newPath + '-editted' + '.jpg',final)


def images_iterator(imgsPath,finalPath):
	imgs = os.listdir(imgsPath)
	for i in range (0, len(imgs)):
		remove_background(imgsPath + '\\' + imgs[i])
		newimgName = ('.').join(imgs[i].split('.')[:-1])

		os.rename(imgsPath + newimgName+ '-editted' + '.jpg', finalPath + imgs[i])

def main():
	path = 'C:\Users\Murtaza\ubuntu-sf\outfitted\\'
	oldtopsDir = path + 'oldTops\\'
	oldbotsDir = path + 'oldBottoms\\'
	topsDir = path + 'Tops\\'
	botsDir = path + 'Bottoms\\'
	images_iterator(oldtopsDir,topsDir)
	images_iterator(oldbotsDir,botsDir)

if __name__ == '__main__':
	main()
