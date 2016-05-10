set datafile separator comma

set style data histograms
set style histogram rowstacked

set nokey

set xlabel "Code Executed"
set ylabel "Percentage of Total Time"

set xtics scale 0

set label center "n" at first 0,((2389130471.0/2)*100/16395658539.0) front
set label center "fib" at first 0,((2389130471.0+1061523265.0/2)*100/16395658539.0) front
set label center "$-$" at first 0,((2389130471.0+1061523265.0+838322845.0/2)*100/16395658539.0) front
set label center "$=$" at first 0,((2389130471.0+1061523265.0+838322845.0+581413644.0/2)*100/16395658539.0) front
set label center "$+$" at first 0,((2389130471.0+1061523265.0+838322845.0+581413644.0+300520436.0/2)*100/16395658539.0) front
set label center "call" at first 1,((3385623571.0/2)*100/16395658539.0) front
set label center "object" at first 1,((3385623571.0+2689362406.0/2)*100/16395658539.0) front
set label center "$=$" at first 3,((1772289715.0/2)*100/16395658539.0) front
set label center "$-$" at first 3,((1772289715.0+895027630.0/2)*100/16395658539.0) front
set label center "$+$" at first 3,((1772289715.0+895027630.0+457253836.0/2)*100/16395658539.0) front

set terminal epslatex 12 size 6.25, 3in
set output "fib-30-profile.tex"

plot "<awk -F, '{if ($2==\"1\") print $0}' profiled-fib-30-sum.csv" using ($4*100/16395658539.0):xticlabels(1):key(1) lc rgbcolor "black",\
     "<awk -F, '{if ($2==\"2\") print $0}' profiled-fib-30-sum.csv" using ($4*100/16395658539.0) lc rgbcolor "black",\
     "<awk -F, '{if ($2==\"3\") print $0}' profiled-fib-30-sum.csv" using ($4*100/16395658539.0) lc rgbcolor "black",\
     "<awk -F, '{if ($2==\"4\") print $0}' profiled-fib-30-sum.csv" using ($4*100/16395658539.0) lc rgbcolor "black",\
     "<awk -F, '{if ($2==\"5\") print $0}' profiled-fib-30-sum.csv" using ($4*100/16395658539.0) lc rgbcolor "black"
