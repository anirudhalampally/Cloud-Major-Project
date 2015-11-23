import sys,os
from nltk.corpus import wordnet
import operator
#print wordnet.synsets("canada")[0].wup_similarity(wordnet.synsets("canada")[0])
f=open("earthquake.txt")
similarity={}
for line in f:
    x=line.split(" ")
    val=0
    first=0
    g=open("earthquake.txt")
    for line1 in g:
        y=line1.split(" ")
        if line==line1 and first==1:
	    try:
		first=1
	    	val+=similarity[line]
		break
	    except:
            	break
        for i in x:
            for j in y:
                try:
                    val+=wordnet.synsets(i)[0].wup_similarity(wordnet.synsets(j)[0])
                except:
                    val=val
    #print val
    similarity[line] = val

sorted_x = sorted(similarity.items(), key=operator.itemgetter(1),reverse=True)
print sorted_x[0][0]
print sorted_x[1][0]

