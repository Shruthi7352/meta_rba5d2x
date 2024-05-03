require at91bootstrap.inc

LIC_FILES_CHKSUM = "file://main.c;endline=27;md5=a2a70db58191379e2550cbed95449fbd"

BBRANCH = "at91bootstrap-rba5d2x_v3.8.12"

SRC_URI = "git://github.com/rugged-board/at91bootstrap-rba5d2x.git;protocol=https;branch=${BBRANCH}"

PV = "3.8.12+git${SRCPV}"

SRCREV = "15d9f256b786332e7154d7cf49e1a5380b074df7"

S = "${WORKDIR}/git"

COMPATIBLE_MACHINE = '(sama5d3xek|sama5d3-xplained|sama5d3-xplained-sd|at91sam9x5ek|at91sam9rlek|at91sam9m10g45ek|sama5d4ek|sama5d4-xplained|sama5d4-xplained-sd|sama5d2-xplained|sama5d2-xplained-sd|sama5d2-xplained-emmc|sama5d2-ptc-ek|sama5d2-ptc-ek-sd|sama5d27-som1-ek|sama5d27-som1-ek-sd|rugged-board-a5d2x)'
