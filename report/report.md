---
title: "Timsort"
author: "Group 5"
toc: true
toc-depth: 2
number-sections: true
---


- [] Algorithm explanation with examples
- [] Time complexity analysis
- [] Java implementation
- [x] Testing methodology
- [] Performance results for 5, 1,000, and 1,000,000 elements
- [] Comparison with Bubble Sort, Selection Sort, and Insertion Sort
- [] Appropriate and inappropriate use cases
- [] Real-world applications
- [] Conclusion and findings


# Introduction

Timsort is a hybrid, stable sorting algorithm, derived from merge sort and insertion sort, designed to perform well on many kinds of real-world data. It was implemented by Tim Peters in 2002 for use in the Python programming language. The algorithm finds subsequences of the data that are already ordered (runs) and uses them to sort the remainder more efficiently.

# Methodology

## Empirical Run-Time Testing Methodology

### Data generation

The most obvious data set we should use is integers, however, that begs the question, what should be the range of the randomly generated integers? 

Suppose that we generate random integers in the range between $[1, k]$ and we generate a list of size $n$. If $k >> n$, then we would rarely get duplicates, meanwhile, if $k << n$, then we would get many duplicates.

Our parameter will then be the ratio $r = \frac{n}{k}$ where a high value of $r$ would mean more duplicates (I think the duplicate can be thought of as the expected number of duplicates, although, I'm not too certain on the probabilistic analysis on that).

In our case, we selected 2 duplication ratios:

- $r = 10$ for data with high duplication
- $r = 0.1$ for data with low duplication

For each duplication ratios, we will use datasets of the following sizes:

- $n = 5$ for small datasets (for data with high duplication, $k = 1$, meaning that the data will just be an array containing just 1)
- $n = 1000$ for medium sized datasets
- $n = 1000000$ for large datasets

### Computer Specifications

To make sure that the run time results aren't too much affected by other external factors, we ran the same algorithms on the same computer.

The most important variable for run time in this case is probably the CPU, and the speed and size of the RAM.

In our case, we used a CPU with the following `lscpu` output:

```
Architecture:                x86_64
  CPU op-mode(s):            32-bit, 64-bit
  Address sizes:             39 bits physical, 48 bits virtual
  Byte Order:                Little Endian
CPU(s):                      6
  On-line CPU(s) list:       0-5
Vendor ID:                   GenuineIntel
  Model name:                Intel(R) Core(TM) i5-9500 CPU @ 3.00GHz
    CPU family:              6
    Model:                   158
    Thread(s) per core:      1
    Core(s) per socket:      6
    Socket(s):               1
    Stepping:                10
    Microcode version:       0xfa
    CPU(s) scaling MHz:      20%
    CPU max MHz:             4400.0000
    CPU min MHz:             800.0000
    BogoMIPS:                6000.00
    Flags:                   fpu vme de pse tsc msr pae mce cx8 apic sep mtrr pge mca
                              cmov pat pse36 clflush dts acpi mmx fxsr sse sse2 ss ht
                              tm pbe syscall nx pdpe1gb rdtscp lm constant_tsc art ar
                             ch_perfmon pebs bts rep_good nopl xtopology nonstop_tsc 
                             cpuid aperfmperf pni pclmulqdq dtes64 monitor ds_cpl vmx
                              smx est tm2 ssse3 sdbg fma cx16 xtpr pdcm pcid sse4_1 s
                             se4_2 x2apic movbe popcnt tsc_deadline_timer aes xsave a
                             vx f16c rdrand lahf_lm abm 3dnowprefetch cpuid_fault epb
                              pti ssbd ibrs ibpb stibp tpr_shadow flexpriority ept vp
                             id ept_ad fsgsbase tsc_adjust bmi1 avx2 smep bmi2 erms i
                             nvpcid mpx rdseed adx smap clflushopt intel_pt xsaveopt 
                             xsavec xgetbv1 xsaves dtherm ida arat pln pts hwp hwp_no
                             tify hwp_act_window hwp_epp vnmi md_clear flush_l1d arch
                             _capabilities
Virtualization features:     
  Virtualization:            VT-x
Caches (sum of all):         
  L1d:                       192 KiB (6 instances)
  L1i:                       192 KiB (6 instances)
  L2:                        1.5 MiB (6 instances)
  L3:                        9 MiB (1 instance)
NUMA:                        
  NUMA node(s):              1
  NUMA node0 CPU(s):         0-5
Vulnerabilities:             
  Gather data sampling:      Mitigation; Microcode
  Ghostwrite:                Not affected
  Indirect target selection: Not affected
  Itlb multihit:             KVM: Mitigation: Split huge pages
  L1tf:                      Mitigation; PTE Inversion; VMX conditional cache flushes
                             , SMT disabled
  Mds:                       Mitigation; Clear CPU buffers; SMT disabled
  Meltdown:                  Mitigation; PTI
  Mmio stale data:           Mitigation; Clear CPU buffers; SMT disabled
  Old microcode:             Not affected
  Reg file data sampling:    Not affected
  Retbleed:                  Mitigation; IBRS
  Spec rstack overflow:      Not affected
  Spec store bypass:         Mitigation; Speculative Store Bypass disabled via prctl
  Spectre v1:                Mitigation; usercopy/swapgs barriers and __user pointer 
                             sanitization
  Spectre v2:                Mitigation; IBRS; IBPB conditional; STIBP disabled; RSB 
                             filling; PBRSB-eIBRS Not affected; BHI Not affected
  Srbds:                     Mitigation; Microcode
  Tsa:                       Not affected
  Tsx async abort:           Mitigation; TSX disabled
  Vmscape:                   Mitigation; IBPB before exit to userspace
```

As for the RAM, the output of `sudo dmidecode --type 17` is as follows:

```
Getting SMBIOS data from sysfs.
SMBIOS 3.1.1 present.

Handle 0x000A, DMI type 17, 40 bytes
Memory Device
	Array Handle: 0x0009
	Error Information Handle: Not Provided
	Total Width: 64 bits
	Data Width: 64 bits
	Size: 8 GiB
	Form Factor: DIMM
	Set: None
	Locator: DIMM1
	Bank Locator: Not Specified
	Type: DDR4
	Type Detail: Synchronous
	Speed: 2666 MT/s
	Manufacturer: 80CE000080CE
	Serial Number: 343C8B13
	Asset Tag: 02193900
	Part Number: M378A1K43DB2-CTD    
	Rank: 1
	Configured Memory Speed: 2666 MT/s
	Minimum Voltage: Unknown
	Maximum Voltage: Unknown
	Configured Voltage: 1.2 V

Handle 0x000B, DMI type 17, 40 bytes
Memory Device
	Array Handle: 0x0009
	Error Information Handle: Not Provided
	Total Width: Unknown
	Data Width: Unknown
	Size: No Module Installed
	Form Factor: Unknown
	Set: None
	Locator: DIMM2
	Bank Locator: Not Specified
	Type: Unknown
	Type Detail: None
```

The OS might also be a factor. We used a Linux kernel which has a `uname -a` output as follows:

```
7.2.3-arch1-3
```

The test would be decently fair when tested one after another because the CPU and memory utilization for other processes would be similar.

### Data Collection Methods

Because of the randomness of the dataset, and the randomness of OS scheduling, it is necessary to run the code multiple time and record the average of all notable metrics alongside with its standard deviation.

We will run perform each run 100 times to ensure a good sample size.

# Time complexity analysis

# Performance results


## Runtime

With $r = 0.1$:

| Sorting Algorithms \ Sizes |                   $5$ |                $1000$ |             $1000000$ |
| -------------------------- | --------------------: | --------------------: | --------------------: |
| `bubbleSort`               | $1.13e-5 \pm 7.90e-5$ | $2.31e-3 \pm 9.53e-4$ |                   N/A |
| `insertionSort`            | $5.35e-6 \pm 7.69e-6$ | $8.43e-4 \pm 7.57e-4$ |                   N/A |
| `javaSort`                 | $4.09e-4 \pm 4.06e-3$ | $2.28e-4 \pm 8.95e-5$ | $2.68e-1 \pm 2.25e-2$ |
| `mergeSort`                | $5.06e-6 \pm 8.06e-6$ | $6.32e-4 \pm 1.84e-4$ | $4.53e-1 \pm 1.41e-2$ |
| `selectionSort`            | $3.90e-6 \pm 6.84e-6$ | $1.24e-3 \pm 8.31e-4$ |                   N/A |
| `timSort`                  | $1.11e-5 \pm 4.69e-5$ | $2.90e-4 \pm 9.38e-5$ | $4.55e-1 \pm 2.59e-2$ |

With $r = 10$:

| Sorting Algorithms \ Sizes |                   $5$ |                $1000$ |             $1000000$ |
| -------------------------- | --------------------: | --------------------: | --------------------: |
| `bubbleSort`               | $3.29e-6 \pm 7.40e-6$ | $3.09e-3 \pm 1.01e-3$ |                   N/A |
| `insertionSort`            | $3.24e-6 \pm 6.32e-6$ | $8.74e-4 \pm 6.65e-4$ |                   N/A |
| `javaSort`                 | $1.47e-6 \pm 4.62e-6$ | $9.27e-5 \pm 1.41e-5$ | $2.73e-1 \pm 2.41e-2$ |
| `mergeSort`                | $4.87e-7 \pm 9.35e-8$ | $1.70e-4 \pm 1.24e-5$ | $4.50e-1 \pm 2.07e-2$ |
| `selectionSort`            | $4.73e-6 \pm 1.13e-5$ | $1.31e-3 \pm 8.29e-4$ |                   N/A |
| `timSort`                  | $2.45e-6 \pm 4.37e-6$ | $1.69e-4 \pm 1.82e-5$ | $4.39e-1 \pm 1.03e-2$ |

## Comparisons

With $r = 0.1$:

| Sorting Algorithms \ Sizes |              $5$ |              $1000$ |             $1000000$ |
| -------------------------- | ---------------: | ------------------: | --------------------: |
| `bubbleSort`               |       $10 \pm 0$ |     $4.995e5 \pm 0$ |                   N/A |
| `insertionSort`            | $13.47 \pm 3.00$ | $1.906e4 \pm 44.92$ |                   N/A |
| `javaSort`                 |              N/A |                 N/A |                   N/A |
| `mergeSort`                |  $7.18 \pm 0.85$ | $8.709e3 \pm 16.35$ |  $1.867e7 \pm 555.35$ |
| `selectionSort`            |       $14 \pm 0$ |     $5.005e5 \pm 0$ |                   N/A |
| `timSort`                  | $16.35 \pm 2.96$ | $1.342e4 \pm 67.41$ | $2.343e7 \pm 1.627e3$ |

With $r = 10$:

| Sorting Algorithms \ Sizes |        $5$ |              $1000$ |             $1000000$ |
| -------------------------- | ---------: | ------------------: | --------------------: |
| `bubbleSort`               | $10 \pm 0$ |     $4.995e5 \pm 0$ |                   N/A |
| `insertionSort`            |  $4 \pm 0$ | $1.894e4 \pm 71.26$ |                   N/A |
| `javaSort`                 |        N/A |                 N/A |                   N/A |
| `mergeSort`                |  $7 \pm 0$ | $8.701e3 \pm 16.12$ |  $1.867e7 \pm 611.02$ |
| `selectionSort`            | $14 \pm 0$ |     $5.005e5 \pm 0$ |                   N/A |
| `timSort`                  |  $4 \pm 0$ | $1.337e4 \pm 75.44$ | $2.343e7 \pm 2.075e3$ |

## Swaps

With $r = 0.1$:

| Sorting Algorithms \ Sizes |             $5$ |                $1000$ |             $1000000$ |
| -------------------------- | --------------: | --------------------: | --------------------: |
| `bubbleSort`               | $4.92 \pm 1.98$ | $2.492e5 \pm 5.133e3$ |                   N/A |
| `insertionSort`            |       $0 \pm 0$ |             $0 \pm 0$ |                   N/A |
| `javaSort`                 |             N/A |                   N/A |                   N/A |
| `mergeSort`                |       $0 \pm 0$ |             $0 \pm 0$ |             $0 \pm 0$ |
| `selectionSort`            | $2.97 \pm 0.66$ |     $992.43 \pm 2.36$ |                   N/A |
| `timSort`                  | $1.14 \pm 1.00$ |    $246.72 \pm 42.51$ | $2.500e5 \pm 1.366e3$ |

With $r = 10$:

| Sorting Algorithms \ Sizes |       $5$ |                $1000$ |             $1000000$ |
| -------------------------- | --------: | --------------------: | --------------------: |
| `bubbleSort`               | $0 \pm 0$ | $2.467e5 \pm 4.822e3$ |                   N/A |
| `insertionSort`            | $0 \pm 0$ |             $0 \pm 0$ |                   N/A |
| `javaSort`                 |       N/A |                   N/A |                   N/A |
| `mergeSort`                | $0 \pm 0$ |             $0 \pm 0$ |             $0 \pm 0$ |
| `selectionSort`            | $3 \pm 0$ |     $993.42 \pm 2.21$ |                   N/A |
| `timSort`                  | $0 \pm 0$ |    $247.52 \pm 44.52$ | $2.499e5 \pm 1.244e3$ |

## Copies (including swaps)

With $r = 0.1$:

| Sorting Algorithms \ Sizes |              $5$ |                $1000$ |             $1000000$ |
| -------------------------- | ---------------: | --------------------: | --------------------: |
| `bubbleSort`               | $14.76 \pm 5.95$ | $7.477e5 \pm 1.540e4$ |                   N/A |
| `insertionSort`            | $11.04 \pm 3.38$ | $2.517e5 \pm 4.861e3$ |                   N/A |
| `javaSort`                 |              N/A |                   N/A |                   N/A |
| `mergeSort`                |       $24 \pm 0$ |       $1.995e4 \pm 0$ |       $3.990e7 \pm 0$ |
| `selectionSort`            |  $8.91 \pm 1.98$ |    $2.977e3 \pm 7.07$ |                   N/A |
| `timSort`                  | $11.45 \pm 4.47$ |  $2.014e4 \pm 224.68$ | $4.016e7 \pm 6.637e3$ |

With $r = 10$:

| Sorting Algorithms \ Sizes |        $5$ |                $1000$ |             $1000000$ |
| -------------------------- | ---------: | --------------------: | --------------------: |
| `bubbleSort`               |  $0 \pm 0$ | $7.402e5 \pm 1.447e4$ |                   N/A |
| `insertionSort`            |  $0 \pm 0$ | $2.500e5 \pm 4.928e3$ |                   N/A |
| `javaSort`                 |        N/A |                   N/A |                   N/A |
| `mergeSort`                | $24 \pm 0$ |       $1.995e4 \pm 0$ |       $3.990e7 \pm 0$ |
| `selectionSort`            |  $9 \pm 0$ |    $2.980e3 \pm 6.64$ |                   N/A |
| `timSort`                  |  $0 \pm 0$ |  $2.004e4 \pm 205.08$ | $4.016e7 \pm 6.658e3$ |

## Copies (excluding swaps)

With $r = 0.1$:

| Sorting Algorithms \ Sizes |              $5$ |                $1000$ |             $1000000$ |
| -------------------------- | ---------------: | --------------------: | --------------------: |
| `bubbleSort`               |        $0 \pm 0$ |             $0 \pm 0$ |                   N/A |
| `insertionSort`            | $11.04 \pm 3.38$ | $2.517e5 \pm 4.861e3$ |                   N/A |
| `javaSort`                 |              N/A |                   N/A |                   N/A |
| `mergeSort`                |       $24 \pm 0$ |       $1.995e4 \pm 0$ |       $3.990e7 \pm 0$ |
| `selectionSort`            |        $0 \pm 0$ |             $0 \pm 0$ |                   N/A |
| `timSort`                  |  $8.03 \pm 2.91$ |  $1.940e4 \pm 173.41$ | $3.941e7 \pm 5.546e3$ |

With $r = 10$:

| Sorting Algorithms \ Sizes |        $5$ |                $1000$ |             $1000000$ |
| -------------------------- | ---------: | --------------------: | --------------------: |
| `bubbleSort`               |  $0 \pm 0$ |             $0 \pm 0$ |                   N/A |
| `insertionSort`            |  $0 \pm 0$ | $2.500e5 \pm 4.928e3$ |                   N/A |
| `javaSort`                 |        N/A |                   N/A |                   N/A |
| `mergeSort`                | $24 \pm 0$ |       $1.995e4 \pm 0$ |       $3.990e7 \pm 0$ |
| `selectionSort`            |  $0 \pm 0$ |             $0 \pm 0$ |                   N/A |
| `timSort`                  |  $0 \pm 0$ |  $1.929e4 \pm 162.64$ | $3.941e7 \pm 5.402e3$ |

# Conclusion
