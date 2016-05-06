set datafile separator comma

set style data histograms
set style histogram rowstacked

set nokey

set xlabel "Code"
set ylabel "Time / s"

set xtics scale 0

set label center "n" at first 0,(2389130471.0e-9/2) front
set label center "fib" at first 0,(2389130471.0e-9+1061523265.0e-9/2) front
set label center "$-$" at first 0,(2389130471.0e-9+1061523265.0e-9+838322845.0e-9/2) front
set label center "$=$" at first 0,(2389130471.0e-9+1061523265.0e-9+838322845.0e-9+581413644.0e-9/2) front
set label center "$+$" at first 0,(2389130471.0e-9+1061523265.0e-9+838322845.0e-9+581413644.0e-9+300520436.0e-9/2) front
set label center "call" at first 1,(3385623571.0e-9/2) front
set label center "object" at first 1,(3385623571.0e-9+2689362406.0e-9/2) front
set label center "$=$" at first 3,(1772289715.0e-9/2) front
set label center "$-$" at first 3,(1772289715.0e-9+895027630.0e-9/2) front
set label center "$+$" at first 3,(1772289715.0e-9+895027630.0e-9+457253836.0e-9/2) front

set terminal epslatex 12 size 6.25, 3in
set output "fib-30-profile.tex"

plot "<awk -F, '{if ($2==\"1\") print $0}' profiled-fib-30-sum.csv" using ($4*1e-9):xticlabels(1):key(1) lc rgbcolor "black",\
     "<awk -F, '{if ($2==\"2\") print $0}' profiled-fib-30-sum.csv" using ($4*1e-9) lc rgbcolor "black",\
     "<awk -F, '{if ($2==\"3\") print $0}' profiled-fib-30-sum.csv" using ($4*1e-9) lc rgbcolor "black",\
     "<awk -F, '{if ($2==\"4\") print $0}' profiled-fib-30-sum.csv" using ($4*1e-9) lc rgbcolor "black",\
     "<awk -F, '{if ($2==\"5\") print $0}' profiled-fib-30-sum.csv" using ($4*1e-9) lc rgbcolor "black"
