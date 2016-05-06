set datafile separator comma

set xrange [-0.5:3.5]

set key off

set xtic ("\\vspace{-1.5\\baselineskip}Java to Java" 0, "Java to Scheme" 1, "\\vspace{-1.5\\baselineskip}Scheme to Scheme" 2, "Scheme to Java" 3)
set xtic scale 0

set ylabel "Execution time / ns"

set logscale y 10

pt=2
ps=1
col="#000000"

set terminal epslatex 12 size 6.25in, 3in
set output "intercallOverhead.tex"

set style fill transparent solid 0.5 noborder

plot "sampled-java-java.log"			volatile	using (0):1	with points pointsize ps pointtype pt linecolor col,\
      "sampled-java-scheme-2.log"		volatile	using (1):1	with points pointsize ps pointtype pt linecolor col,\
      "sampled-scheme-scheme-2.log"	volatile	using (2):1	with points pointsize ps pointtype pt linecolor col,\
      "sampled-scheme-java.log"		volatile	using (3):1	with points pointsize ps pointtype pt linecolor col
