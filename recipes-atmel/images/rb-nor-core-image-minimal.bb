SUMMARY = "A small image just capable of allowing a device to boot."

IMAGE_INSTALL = "packagegroup-core-boot ${CORE_IMAGE_EXTRA_INSTALL} \
		sqlite3 \
		mraa-data \
		python3-mraa-data \
		upm-data \
		python3-upm-data \
		wpa-supplicant \
		paho-mqtt-c \
		firmware \
		e2fsprogs \
		kernel-modules \
		lighttpd-data \
		lighttpd-data-dbg \
		libmodbus-data \
		data-image \
"

IMAGE_LINGUAS = " "

LICENSE = "MIT"

inherit core-image

IMAGE_ROOTFS_SIZE ?= "8192"
IMAGE_ROOTFS_EXTRA_SPACE_append = "${@bb.utils.contains("DISTRO_FEATURES", "systemd", " + 4096", "" ,d)}"
