set datafile separator comma

set timefmt "%Y-%m-%d"

#set xdata time
#set format x "%B"
#set xtics
#set xlabel "Month"
set xdata time
set xlabel 'Work Package Number'
set xtics ("3" "2015-11-23")
set xtics add ("4" "2015-12-07")
set xtics add ("5" "2015-12-21")
set xtics add ("6" "2016-01-04")
set xtics add ("7" "2016-01-18")
set xtics add ("8" "2016-02-01")
set xtics add ("9" "2016-02-15")
set xtics add ("10" "2016-02-29")
set xtics add ("11" "2016-03-14")
set xtics add ("12" "2016-03-28")
set xtics add ("13" "2016-04-11")
set xtics add ("14" "2016-04-25")
# Offset to middle of work package
set xtics offset first 7*24*60*60
set xrange ["2015-11-23":"2016-05-08"]

set ylabel 'Lines Changed'
set ytics rotate 1000
set mytics 2

set style fill pattern 2

@ARG2

$refactor<<EOD
date,added,deleted,commits
2015-12-24,1442,1138,118
2015-12-31,3846,3786,254
2016-01-08,0,70,28
2016-01-17,5282,5328,318
2016-01-18,14,14,12
2016-01-21,4078,858,190
2016-01-22,664,224,52
2016-02-07,944,98,70
2016-02-08,534,538,88
2016-02-09,2758,3262,194
2016-02-21,794,196,48
2016-02-26,222,66,24
2016-03-21,1202,266,94
2016-03-24,18,20,16
2016-03-29,874,244,74
2016-04-01,770,1640,88
2016-04-02,1138,788,104
2016-04-03,1488,588,106
2016-04-04,536,48,10
2016-04-21,204,116,58
2016-04-22,1436,1642,218
2016-04-23,850,322,150
EOD

$eval<<EOD
date,added,deleted,commits
2016-02-20,100,14,12
2016-03-25,620,178,52
2016-03-29,874,244,74
2016-03-31,2146,1132,194
2016-04-02,1138,788,104
2016-04-03,1488,588,106
2016-04-21,204,116,58
2016-04-24,508,202,50
EOD

load ARG3

set palette defined ( 0 '#D73027', 1 "white", 2 '#1A9850' )

max(x,y) = x<y?y:x

plot ARG1 using "date":"added" with lines linecolor rgb '#000000' title "Gross additions",\
	'' using "date":"deleted" with lines linecolor rgb '#000000' title "Gross deletions", \
	'' using "date":"added":"deleted" with filledcurves above linecolor rgb '#1A9850' title "Net additions", \
	'' using "date":"added":"deleted" with filledcurves below linecolor rgb '#D73027' title "Net deletions", \
	$refactor using "date":(max(column("added"),column("deleted"))) with points pointtype 4 pointsize 2 linecolor rgb '#000000' title "Refactors", \
	$eval using "date":(max(column("added"),column("deleted"))) with points pointtype 3 pointsize 2 linecolor rgb '#000000' title "Evaluation", \
