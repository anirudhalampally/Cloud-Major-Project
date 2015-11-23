import sys,operator

fo=open(sys.argv[1],"r")
tweetlist={}
txt=""
for lines in fo.readlines():
	val=lines.split("(")[1].split(")")[0]
	values=val.split(",")
#	txt=values[0].strip()+","+values[1].strip(" ")
	tweetlist[values[0].strip()]=int(values[1].strip(" "))
#for i in tweetlist:
#	print i
#tweetlist.sort(key=lambda x: x[1])
sorted_x = sorted(tweetlist.items(), key=operator.itemgetter(1),reverse=True)
#for i in sorted_x:
#	print i

tweets=[]
val=""
for i in range(4):
	val=sorted_x[i][0][2:-1]
	print val
	tweets.append(val)
ft=open("tweets1.txt","r")
tweets_tag={}
for i in range(len(tweets)):
	tweets_tag[str(int(i+1))]=[]
for tweet in ft.readlines():		
	values=tweet.strip().split(" ")
	for i in range(len(tweets)):
		if tweets[i] in values:
		#	print tweet
			tweets_tag[str(int(i+1))].append(tweet)

for i in range(len(tweets)):
	for x in tweets_tag[str(int(i+1))]:
		print x,

