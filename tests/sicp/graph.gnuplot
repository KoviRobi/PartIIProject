set datafile separator comma

set style data histograms
set style histogram rowstacked

unset tics
unset border
unset key

set label center "Front-end" at graph 0.5, first (45268563/2) rotate by 180
set label center "Middle" at graph 0.5, first (45268563+189016114/2) rotate
set label center "Back-end" at graph 0.5, first (45268563+189016114+608470137/2) rotate

set terminal epslatex 12 size 2in, 6.25in
set output "compiler-stages.tex"

plot 'stages.log' using 1 title "Front-end",\
    '' using 2 title "Middle" ,\
    '' using 3 title "Back-end"