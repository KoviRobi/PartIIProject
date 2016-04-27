set datafile separator comma

set timefmt "%Y-%m-%d"

set tics front

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

$id << EOD
wp, start
3, 2015-11-23
14, 2016-04-25
EOD

set ylabel "References"
set yrange [2.5:10.5]

set palette defined ( 0 '#D73027', 1 "white", 2 '#1A9850' )

set cblabel "Net Lines Added"
set cbrange [400:-400]
set cbtics -200,200,200
set cbtics add ("$>400$" 400, "$>-400$" -400)

#set view map

set title "Net Lines Added (From Commit Messages)"

unset key

@ARG1

m=1
c=-840
f(x)=m*x+c
set fit errorvariables
fit f(x) 'combined2.log' using "date":"workpackage":(abs(400/((column("added")-column("deleted"))**2))) via m,c
update "est_error.log"

plot 'combined.log' using "date":"workpackage":(column("added")-column("deleted")) with image, \
      $id using (column("start")):(column("wp")-0.5) with lines linecolor "black", \
      f(x) with lines linetype "dashed"
