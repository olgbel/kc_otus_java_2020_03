-Xlog:gc=debug:file=./logs/gc-%p-%t.log:tags,uptime,time,level:filecount=5,filesize=10m
-XX:+HeapDumpOnOutOfMemoryError
-XX:HeapDumpPath=./logs/dump

для анализа логов использовался сервис https://gceasy.io/

==================================================
===================Serial GC===================
==================================================
------------------small heap size----------------
-Xms512m
-Xmx512m

приложение упало с ООМ через 2 min 176 ms

Avg Pause GC Time 	345 ms
Max Pause GC Time 	853 ms
Throughput  : 97.419%

    Minor GC stats                              |                Full GC stats                  |          Total GC stats 
Minor GC count	            6                   |   Full GC Count	            3               |   Total GC count 	        9
Minor GC reclaimed 	        187 mb              |   Full GC reclaimed 	        134 mb          |   Total reclaimed bytes 	321 mb
Minor GC total time	        703 ms              |   Full GC total time	        2 sec 398 ms    |   Total GC time 	        3 sec 102 ms
Minor GC avg time 	        117 ms              |   Full GC avg time 	        799 ms          |   Avg GC time 	        345 ms
Minor GC avg time std dev	87.2 ms             |   Full GC avg time std dev	49.4 ms         |   GC avg time std dev	    331 ms
Minor GC min/max time	    0.0580 ms / 233 ms  |   Full GC min/max time	    734 ms / 853 ms |   GC min/max time	        0.0580 ms / 853 ms
Minor GC Interval avg 	    20 sec 266 ms       |   Full GC Interval avg 	    12 sec 549 ms   |   GC Interval avg time 	12 sec 865 ms

    GC Pause Statistics                                        
Pause Count	            9                          
Pause total time	    3 sec 102 ms               
Pause avg time 	        345 ms                     
Pause avg time std dev	0.0                       
Pause min/max time	    0.0580 ms / 853 ms                                                            
                                                    
------------------average heap size----------------                               
-Xms3072m
-Xmx3072m

упало с ООМ через 18 min 56 sec 60 ms

Avg Pause GC Time 	4 sec 805 ms
Max Pause GC Time 	7 sec 259 ms
Throughput  : 78.008%

    Minor GC stats                                  |               Full GC stats                   |           Total GC stats
Minor GC count	            6                       |   Full GC Count	        46                  |   Total GC count 	52
Minor GC reclaimed 	        958 mb                  |   Full GC reclaimed 	    4.26 gb             |   Total reclaimed bytes 	5.19 gb
Minor GC total time	        3 sec 172 ms            |   Full GC total time	    4 min 6 sec 665 ms  |   Total GC time 	4 min 9 sec 837 ms
Minor GC avg time 	        529 ms                  |   Full GC avg time 	    5 sec 362 ms        |   Avg GC time 	4 sec 805 ms
Minor GC avg time std dev	559 ms                  |   Full GC avg time std dev	899 ms          |   GC avg time std dev	1 sec 771 ms
Minor GC min/max time	    0.0590 ms / 1 sec 375 ms|   Full GC min/max time	865 ms / 7 sec 259 ms|  GC min/max time	0.0590 ms / 7 sec 259 ms
Minor GC Interval avg 	    1 min 50 sec 409 ms     |   Full GC Interval avg 	14 sec 269 ms       |   GC Interval avg time 	20 sec 118 ms

    GC Pause Statistics
Pause Count	52
Pause total time	4 min 9 sec 837 ms
Pause avg time 	4 sec 805 ms
Pause avg time std dev	0.0
Pause min/max time	0.0590 ms / 7 sec 259 ms

------------------large heap size----------------                               
-Xms20480m
-Xmx20480m

не достигло ООМ, за 10 минут работы GC ни разу не запустился
                                                   
=================================================
===================G1 GC===================
=================================================

------------------small heap size----------------
-Xms512m
-Xmx512m
-XX:+UseG1GC

приложение упало с OOM через 2 min 17 sec 112 ms
[2020-05-01T19:34:07.702+0300][0.014s][debug][gc] ConcGCThreads: 1 offset 8
[2020-05-01T19:34:07.702+0300][0.014s][debug][gc] ParallelGCThreads: 4

Avg Pause GC Time 	44.0 ms
Max Pause GC Time 	507 ms
Throughput  : 99.069%

                Young GC        Concurrent          Full GC         Remark          Cleanup 
Total Time 	    8 sec 507 ms	6 sec 646 ms	    507 ms	        5.89 ms	        0.979 ms
Avg Time 	    354 ms	        1 sec 329 ms	    254 ms	        1.18 ms	        0.196 ms
Std Dev Time	579 ms	        440 ms	            253 ms	        0.529 ms	    0.0206 ms
Min Time 	    6.44 ms	        855 ms	            0.654 ms	    0.781 ms	    0.170 ms
Max Time 	    2 sec 51 ms	    2 sec 50 ms	        507 ms	        2.22 ms	        0.227 ms
Count 	        24	            5	                2	            5	            5

Summary for Pause Time (G1 GC stops application threads during following phases: Young GC, Initial Mark, Remark, Cleanup)
Total Time	    1 sec 276 ms
Avg Time	    44.0 ms
Std Dev Time	91.0 ms
Min Time	    0.170 ms
Max Time	    507 ms

Summary for Concurrent Time (Following phases of G1 GC are concurrently executed with out stopping application threads: Concurrent Marking, Root Region Scanning)
Total Time	    6 sec 646 ms
Avg Time	    1 sec 108 ms
Std Dev Time	638 ms
Min Time	    0.654 ms
Max Time	    2 sec 50 ms

------------------average heap size----------------                               
-Xms3072m
-Xmx3072m

не достигло ООМ за 15 min 55 sec 447 ms

[2020-05-01T20:52:23.350+0300][0.016s][debug][gc] ConcGCThreads: 1 offset 8
[2020-05-01T20:52:23.351+0300][0.016s][debug][gc] ParallelGCThreads: 4

Avg Pause GC Time 	262 ms
Max Pause GC Time 	714 ms
Throughput  : 99.423%

	            Young GC 	    Concurrent	    Remark 	    Cleanup 
Total Time 	    41 sec 353 ms	35 sec 843 ms	8.35 ms	    3.38 ms
Avg Time 	    2 sec 297 ms	11 sec 948 ms	2.78 ms	    1.13 ms
Std Dev Time	4 sec 417 ms	2 sec 260 ms	0.645 ms	0.0906 ms
Min Time 	    147 ms	        8 sec 818 ms	2.19 ms	    1.00 ms
Max Time 	    14 sec 77 ms	14 sec 73 ms	3.68 ms	    1.22 ms
Count 	        18	            3	            3	        3

Summary for Pause Time (G1 GC stops application threads during following phases: Young GC, Initial Mark, Remark, Cleanup)
Total Time	    5 sec 510 ms
Avg Time	    262 ms
Std Dev Time	201 ms
Min Time	    1.00 ms
Max Time	    714 ms

Summary for Concurrent Time (Following phases of G1 GC are concurrently executed without stopping application threads: Concurrent Marking, Root Region Scanning)
Total Time	    35 sec 843 ms
Avg Time    	11 sec 948 ms
Std Dev Time	2 sec 260 ms
Min Time	    8 sec 818 ms
Max Time	    14 sec 73 ms

------------------large heap size----------------                               
-Xms20480m
-Xmx20480m

не достигло OOM за 8 min 30 sec 584 ms
[2020-05-01T20:32:51.760+0300][0.018s][debug][gc] ConcGCThreads: 1 offset 8
[2020-05-01T20:32:51.760+0300][0.018s][debug][gc] ParallelGCThreads: 4

Avg Pause GC Time 	3 sec 908 ms
Max Pause GC Time 	5 sec 895 ms
Throughput  : 98.469%

	            Young GC 
Total Time 	    7 sec 816 ms
Avg Time 	    3 sec 908 ms
Std Dev Time	1 sec 987 ms
Min Time 	    1 sec 921 ms
Max Time 	    5 sec 895 ms
Count 	        2

Summary for Pause Time (G1 GC stops application threads during following phases: Young GC, Initial Mark, Remark, Cleanup)
Total Time	    7 sec 816 ms
Avg Time	    3 sec 908 ms
Std Dev Time	1 sec 987 ms
Min Time    	1 sec 921 ms
Max Time	    5 sec 895 ms

Summary for Concurrent Time (Following phases of G1 GC are concurrently executed with out stopping application threads: Concurrent Marking, Root Region Scanning)
NO INFO

=================================================
===================Parallel GC===================
=================================================

------------------small heap size----------------
достигло ООМ за 2 min 6 sec 829 ms

Avg Pause GC Time 	674 ms
Max Pause GC Time 	1 sec 601 ms
Throughput  : 95.747%

        Minor GC stats                        |             Full GC stats                       |               Total GC stats
Minor GC count	            4                 |     Full GC Count	4                           | Total GC count 	        8
Minor GC reclaimed 	        165 mb            |     Full GC reclaimed 	162 mb                  | Total reclaimed bytes 	327 mb
Minor GC total time	        743 ms            |     Full GC total time	4 sec 651 ms            | Total GC time 	        5 sec 394 ms
Minor GC avg time 	        186 ms            |     Full GC avg time 	1 sec 163 ms            | Avg GC time 	            674 ms
Minor GC avg time std dev	74.3 ms           |     Full GC avg time std dev	283 ms          | GC avg time std dev	    531 ms
Minor GC min/max time	    83.9 ms / 288 ms  |     Full GC min/max time	830 ms / 1 sec 601 ms| GC min/max time	        83.9 ms / 1 sec 601 ms
Minor GC Interval avg   	19 sec 490 ms     |     Full GC Interval avg 	18 sec 179 ms       | GC Interval avg time 	16 sec 292 ms                         
                               
    GC Pause Statistics
Pause Count	            8
Pause total time	    5 sec 394 ms
Pause avg time 	        674 ms
Pause avg time std dev	0.0
Pause min/max time	    83.9 ms / 1 sec 601 ms

------------------average heap size----------------                               
-Xms3072m
-Xmx3072m    

не достигло ООМ за 16 min 4 sec 602 ms

Avg Pause GC Time 	5 sec 574 ms
Max Pause GC Time 	10 sec 181 ms
Throughput  : 68.217%

                Minor GC stats                    |               Full GC stats                             |         Total GC stats                         
Minor GC count	            4                     | Full GC Count	            51                          | Total GC count 	        55
Minor GC reclaimed 	        1.01 gb               | Full GC reclaimed 	        1.09 gb                     | Total reclaimed bytes 	2.1 gb
Minor GC total time	        8 sec 899 ms          | Full GC total time	        4 min 57 sec 683 ms         | Total GC time 	        5 min 6 sec 582 ms
Minor GC avg time 	        2 sec 225 ms          | Full GC avg time 	        5 sec 837 ms                | Avg GC time 	            5 sec 574 ms
Minor GC avg time std dev	1 sec 223 ms          | Full GC avg time std dev	700 ms                      | GC avg time std dev	    1 sec 201 ms
Minor GC min/max time	    699 ms / 3 sec 927 ms | Full GC min/max time	    5 sec 198 ms / 10 sec 181 ms| GC min/max time	        699 ms / 10 sec 181 ms
Minor GC Interval avg 	    1 min 57 sec 277 ms   | Full GC Interval avg 	    9 sec 931 ms                | GC Interval avg time 	15 sec 899 ms
                     
                                       
    GC Pause Statistics
Pause Count	            55
Pause total time	    5 min 6 sec 582 ms
Pause avg time 	        5 sec 574 ms
Pause avg time std dev	0.0
Pause min/max time	    699 ms / 10 sec 181 ms   

------------------large heap size----------------                               
-Xms20480m
-Xmx20480m      

не достигло ООМ за 9 min 53 sec 495 ms

Avg Pause GC Time 	20 sec 827 ms
Max Pause GC Time 	20 sec 827 ms
Throughput  : 96.491%

                Minor GC stats                              |  Full GC stats    |            Total GC stats 
Minor GC count	            1                               |  NO INFO          | Minor GC count	            1
Minor GC reclaimed 	        2.8 gb                          |                   | Minor GC reclaimed 	        2.8 gb
Minor GC total time	        20 sec 827 ms                   |                   | Minor GC total time	        20 sec 827 ms  
Minor GC avg time 	        20 sec 827 ms                   |                   | Minor GC avg time 	        20 sec 827 ms       
Minor GC avg time std dev	0                               |                   | Minor GC avg time std dev	0                      
Minor GC min/max time	    20 sec 827 ms / 20 sec 827 ms   |                   | Minor GC min/max time	    20 sec 827 ms / 20 sec 827 ms 
Minor GC Interval avg 	    n/a                             |                   | Minor GC Interval avg 	    n/a                

    GC Pause Statistics
Pause Count	            1
Pause total time	    20 sec 827 ms
Pause avg time 	        20 sec 827 ms
Pause avg time std dev	0.0
Pause min/max time	    20 sec 827 ms / 20 sec 827 ms

**********************************************************************************       
Задача:
    в бесконечном цикле сначала добавляем 500000 значений, затем половину удаляем.
Summary по GC:
    1. Serial
        1.1. small heap size
            упал с ООМ за 2 min 176 ms
            Throughput  : 97.419%
            количество STW: 9 (Minor: 6; Full: 3); суммарное время STW: 3 sec 102 ms
        1.2. average heap size
            упал с ООМ за 18 min 56 sec 60 ms
            Throughput  : 78.008%
            количество STW: 52 (Minor: 6, Full: 46); суммарное время STW: 4 min 9 sec 837 ms
        1.3. large heap size
            GC ни разу не запустился
            Throughput  : NO INFO
    2. Parallel
        2.1. small heap size
            упал с ООМ за 2 min 6 sec 829 ms
            Throughput  : 95.747%
            количество STW: 8 (Minor: 4; Full: 4); суммарное время STW: 5 sec 394 ms
        2.2. average heap size
            не упал с ООМ за 16 min 4 sec 602 ms
            Throughput  : 68.217%
            количество STW: 55 (Minor: 4; Full: 51); суммарное время STW: 5 min 6 sec 582 ms
        2.3. large heap size
            не упал с ООМ за 9 min 53 sec 495 ms
            Throughput  : 96.491%
            количество STW: 1 (Minor: 1); суммарное время STW: 20 sec 827 ms
    3. G1    
        3.1. small heap size
            упал с ООМ за 2 min 17 sec 112 ms
            Throughput  : 99.069%
            суммарное время STW: 1 sec 276 ms
        3.2. average heap size
            не упал с ООМ за 15 min 55 sec 447 ms
            Throughput  : 99.423%
            суммарное время STW: 5 sec 510 ms
        3.3. large heap size
            не упал с OOM за 8 min 30 sec 584 ms
            Throughput  : 98.469%
            суммарное время STW: 7 sec 816 ms
            
Заключение:   
    для решения данной задачи лучше использовать G1 GC, потому что:
     -- у него лучшие показатели Throughput
     -- для маленького размера heap программа отработала дольше, чем при остальных GC, что говорит о своевременной работе GC
     -- при разных размерах heap у G1 наименьший STW                
