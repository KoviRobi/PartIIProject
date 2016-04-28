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
set mytics 4

@ARG2

set palette defined ( 0 '#D73027', 1 "white", 2 '#1A9850' )

max(x,y) = x<y?y:x

plot ARG1 using "date":"added" with lines linecolor rgb '#000000' notitle "Gross additions",\
	'' using "date":"deleted" with lines linecolor rgb '#000000' notitle "Gross deletions", \
	'' using "date":"added":"deleted" with filledcurves above fill pattern 3 linecolor rgb '#1A9850' title "Net additions", \
	'' using "date":"added":"deleted" with filledcurves below fill pattern 3 linecolor rgb '#D73027' title "Net deletions", \
	("refactors-".ARG1) using "date":(max(column("added"),column("deleted"))) with points pointtype 4 pointsize 2 linecolor rgb '#000000' title "Refactors", \
	("evals-".ARG1) using "date":(max(column("added"),column("deleted"))) with points pointtype 3 pointsize 2 linecolor rgb '#000000' title "Evaluation", \
