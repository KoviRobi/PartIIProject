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

set terminal epslatex 12 size 6.25, 3in
set output ARG2

load ARG3

plot ARG1 using "date":"added" with lines title "Gross additions",\
	'' using "date":"deleted" with lines title "Gross deletions", \
	'' using "date":"added":"deleted" with filledcurves above title "Net additions", \
	'' using "date":"added":"deleted" with filledcurves below title "Net deletions"
