# TopicIdentification
Topic Identification NLP Project

Algorithm for topic indentification : This algorithm uses both frequency and knowedge based approaches.
Knowledge used here is the ACM-CCS heirarchy of subjects(topics)

Input: Research Paper


1) Preprocessing
  a) Get abstract from a paper using paper-parser library
  b) Get list of terms based on generic stoplist
  c) Stem terms based on Porter's Stemming Algorithm  
  d) Parse acmccs xml file
  
2) Find similarity between generated terms and topics from acm ccs

3) Some weight factor(W) must be used to find most appropriate topic for those corresponding topic.

4) Get topic having maximum weight. 
