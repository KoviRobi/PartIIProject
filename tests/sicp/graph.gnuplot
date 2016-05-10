set datafile separator comma

set style data histograms
set style histogram rowstacked

unset xtics
unset ytics
unset border
unset key

set y2label "Time / ms"
set y2tics rotate
set y2tics scale 0

set label center "Front-end" at graph 0.5, first (45268563e-9/2) rotate by 180
set label center "Middle" at graph 0.5, first (45268563e-9+189016114e-9/2) rotate
set label center "Back-end" at graph 0.5, first (45268563e-9+189016114e-9+608470137e-9/2) rotate

set terminal epslatex 12 size 2in, 6.25in
set output "compiler-stages.tex"

plot [-0.5:0.5] [0:(45268563e-9+189016114e-9+608470137e-9)] 'stages.log' using ($1*1e-9) title "Front-end" linecolor '#000000',\
    '' using ($2*1e-9) title "Middle" linecolor '#000000',\
    '' using ($3*1e-9) title "Back-end" linecolor '#000000'
