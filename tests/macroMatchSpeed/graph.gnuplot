set datafile separator comma

set ylabel "Time to match {\\tt(a ...)} with {\\tt(a$^n$)} / ms"
set ytics rotate

set xlabel "Length of input list, $n$"

# Multiply time units by 1e-6 to have milli and not nanoseconds
stats 'results.log' using 1:(column(2)*1e-6) name "fit"

f(x)=fit_slope*x+fit_intercept
set label 1 sprintf("$r = %4.2f$", fit_correlation)  at graph 0.7, graph 0.9

set terminal epslatex 12 size 6.25, 3in

set key at graph 0.4, graph 0.9

set term push
set term x11
# Multiply time units by 1e-6 to have milli and not nanoseconds
plot 'results.log' using 1:(column(2)*1e-6):(column(3)*1e-6) with errorbars linecolor '#000000' title "Recorded points", \
    f(x) with lines linetype 'dotdash' linecolor '#000000' title "Regression"
set term pop
set output "macro-match-speed-plot.tex"

set yrange [0:GPVAL_Y_MAX]

plot 'results.log' using 1:(column(2)*1e-6):(column(3)*1e-6) with errorbars linecolor '#000000' title "Recorded points", \
    f(x) with lines linetype 'dotdash' linecolor '#000000' title "Regression"
