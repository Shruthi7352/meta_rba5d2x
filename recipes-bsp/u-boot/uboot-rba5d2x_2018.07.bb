require u-boot-atmel.inc

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://README;beginline=1;endline=22;md5=9915e8cb100eb5dbb366010a0f10296c"

SRCREV = "aee7fb19c1e5f95ebc55d7b401257341334beb8b"

PV = "v2018.07-at91+git${SRCPV}"
S = "${WORKDIR}/git"

COMPATIBLE_MACHINE = '(sama5d3xek|sama5d3-xplained|sama5d3-xplained-sd|at91sam9x5ek|at91sam9rlek|at91sam9m10g45ek|sama5d4ek|sama5d4-xplained|sama5d4-xplained-sd|sama5d2-xplained|sama5d2-xplained-sd|sama5d2-xplained-emmc|sama5d2-ptc-ek|sama5d2-ptc-ek-sd|sama5d27-som1-ek|sama5d27-som1-ek-sd|rugged-board-a5d2x|rugged-board-a5d2x-sd1)'

UBRANCH = "uboot-rba5d2x"
SRC_URI = "git://github.com/rugged-board/uboot-rba5d2x.git;protocol=git;branch=${UBRANCH}"
#SRC_URI[md5sum] = "bce280e5386a4a8affd28d539bedc380"

PACKAGE_ARCH = "${MACHINE_ARCH}"
