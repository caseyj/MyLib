#############
#Author: John Casey
#Title: Otsu.r
#Language: R
#Description: a quick command line interface for Otsu's Method to be used on 1 dimensional datasets such as a list of car speeds 
#				recorded at an intersection. The input dataset is a csv delimited by "."'s. 
#Usage: user:> rscript Otsu.r [dataFile.csv] [bin size] [minimum value] [maximum value]
##############


args<-commandArgs(TRUE)
#if not enough args are offered this terminates and informs the user of the proper usage
if(length(args) <4){

	print("Must be called: ")
	stop(">:rscript Otsu.r [dataFile] [binSize integer] [user defined min] [user defined max]")
}

#a few variables in order to properly take in and utilize the arguments
fileName <- args[1]
#the following variables must be numeric
binSize <-as.numeric(args[2])
min<- as.numeric(args[3])
max<- as.numeric(args[4])

#c1 takes in the csv and holds the data in memory
c1<-read.csv(file = fileName, header= FALSE, sep=".", stringsAsFactors=FALSE)
#c2 takes the target data into numeric form for further processing
c2<-as.numeric(c1$V1)
#if a missing value exists, this will omit the value from the set
c2<-na.omit(c2)
#this allows for the data to be binned by the size the user selects between the selected minimum and maximum
#for a bin size of 2 and a min of 38 and a max of 80 this will result in 
#[38-40) [40-42) [42-44)...[78-80)
c3<-cut(c2, breaks = seq(min,max, by=binSize), right= FALSE)

#cut frequencies
c4<-summary(c3)

#histogram data and break down
c5<-hist(c2, breaks = seq(min,max, by=binSize), right=FALSE)

#lenC4 for easy access to how many bins exist
lenC4<-length(c4)

#histMids is for easy access to the medians for each bin
histMids<-c5$mids

#what we hope to use as a comparative value set to R's maximum 
bestMixedVar<- .Machine$integer.max

#the best threshold
bestMixedVar[2]<- .Machine$integer.max

#relative density on histogram/probability
density<- c5$density

mixedVar<-0

for(i in 1:(lenC4-1)){
	#weights on the left side of the threshold
	lW <- sum(density[1:i])
	#control flow for R usage, if i=1 then lVar= 0
	#lVar is the variance of all data below the threshhold
	if(i-1>0){
		lVar <- sd(c4[1:i])^2
	}
	else{
		lVar<- 0
	}
	
	#weights on the right side of the current threshold
	rW <- sum(density[(i+1):lenC4]) - lW

	#control flow for R usage, if i=1 then rVar= 0
	#rVar is the variance of all data below the threshhold
	if(((i+1)-lenC4) <0){
		rVar <- sd(c4[(i+1):lenC4])^2
	}
	else{
		rVar<- 0
	}
	#the variance score of the current threshhold
	mixedVar[1] <- (lW*lVar)+(rW*rVar)
	
	#if mixedVar less than our best, then we have a new best!
	if((mixedVar[1] <= bestMixedVar[1])){
		bestMixedVar[1] <- mixedVar[1]
		bestMixedVar[2] <- i
	}
}
sprintf("The best threshold is %s - %s and the best mixed variance is %s", c5$breaks[bestMixedVar[2]] ,c5$breaks[bestMixedVar[2]]+binSize , bestMixedVar)

