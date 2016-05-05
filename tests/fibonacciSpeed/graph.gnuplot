set datafile separator comma

set ylabel "Time for $n^{\\rm th}$ Fibonacci (Java) / ms"
set y2label "Time for $n^{\\rm th}$ Fibonacci (Scheme) / ms"

set xlabel "Number $ n $"

golden_ratio = (1+sqrt(5))/2

# Multiply time units by 1e-6 to have milli and not nanoseconds
f(x)=a*golden_ratio**(x)
fit f(x) 'results.log' using 1:(column(2)*1e-6) via a
labelf="$" . gprintf("%.1t", a) . "\\times10^{" . gprintf("%.1T", a) . "}\\times{\\varphi}^{n}" . "$"

g(x)=c*golden_ratio**(x)
fit g(x) 'results-scheme-own-stack.log' using 1:(column(2)*1e-6) via c
labelg="$" . gprintf("%.1t", c) . "\\times10^{" . gprintf("%.1T", c) . "}\\times{\\varphi}^{n}" . "$"

set terminal epslatex 12 size 6.25, 3in

set key at graph 0.7, graph 0.9

set output "fibonacci-1.tex"

set yrange [0:f(30.4)]
set y2range [0:g(30.4)]
set ytics rotate 2 nomirror
set mytics 2
set y2tics rotate 1000 nomirror
set my2tics 2

plot 'results.log' using 1:(column(2)*1e-6):(column(3)*1e-6) with errorbars linecolor '#000000' title "Java (".labelf.")", \
    f(x) with lines linetype 'dashed' linecolor '#000000' notitle labelf, \
    'results-scheme-own-stack.log' using 1:(column(2)*1e-6):(column(3)*1e-6) with errorbars title "Scheme (".labelg.")" axes x1y2, \
    g(x) with lines linetype 'dotdash' linecolor '#000000' notitle labelg axes x1y2




# Multiply time units by 1e-6 to have milli and not nanoseconds
f2(x)=a2*golden_ratio**(x)
fit f2(x) 'results-scheme-no-tail-calls.log' using 1:(column(2)*1e-6) via a2
labelf2="$" . gprintf("%.1t", a2) . "\\times10^{" . gprintf("%.1T", a2) . "}\\times{\\varphi}^{n}" . "$"

g2(x)=c2*golden_ratio**(x)
fit g2(x) 'results-scheme-trampolining.log' using 1:(column(2)*1e-6) via c2
labelg2="$" . gprintf("%.1t", c2) . "\\times10^{" . gprintf("%.1T", c2) . "}\\times{\\varphi}^{n}" . "$"

set output "fibonacci-2.tex"

set ylabel "Time for $n^{\\rm th}$ Fibonacci (No Tail-Calls) / ms"
set y2label "Time for $n^{\\rm th}$ Fibonacci (Trampolining) / ms"

set yrange [0:f2(30.4)]
set y2range [0:g2(30.4)]
set ytics 300 nomirror
set y2tics 300 nomirror

plot 'results-scheme-no-tail-calls.log' using 1:(column(2)*1e-6):(column(3)*1e-6) with errorbars linecolor '#000000' title "No Tail-Calls (".labelf2.")", \
    f2(x) with lines linetype 'dashed' linecolor '#000000' notitle labelf2, \
    'results-scheme-trampolining.log' using 1:(column(2)*1e-6):(column(3)*1e-6) with errorbars title "Trampolining (".labelg2.")" axes x1y2, \
    g2(x) with lines linetype 'dotdash' linecolor '#000000' notitle labelg2 axes x1y2
