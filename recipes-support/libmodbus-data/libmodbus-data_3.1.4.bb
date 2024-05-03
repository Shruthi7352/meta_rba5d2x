require libmodbus-data.inc

# Use the stable branch by default
DEFAULT_PREFERENCE = "-1"

SRC_URI[md5sum] = "b1a8fd3a40d2db4de51fb0cbcb201806"
SRC_URI[sha256sum] = "c8c862b0e9a7ba699a49bc98f62bdffdfafd53a5716c0e162696b4bf108d3637"

do_compile(){
	cd ${S}/
	./configure ac_cv_func_malloc_0_nonnull=yes --host=arm-poky-linux-musleabi --enable-static
	make
}

do_install(){
	install -d ${D}/data/lib
	install -d ${D}/usr/lib
	cp ${S}/src/.libs/libmodbus.so.5.1.0 ${D}/data/lib/
	cd ${D}/usr/lib/
	ln -s ./../../data/lib/libmodbus.so.5.1.0 libmodbus.so.5
	ln -s ./../../data/lib/libmodbus.so.5.1.0 libmodbus.so
}

addtask do_delete before do_package_write_rpm after do_package

do_delete(){
	cd ${S}/../../../../../deploy/rpm/cortexa5hf_neon/
	touch libmodbus5-3.1.4-r0.cortexa5hf_neon.rpm
	touch libmodbus-dbg-3.1.4-r0.cortexa5hf_neon.rpm
	touch libmodbus-dev-3.1.4-r0.cortexa5hf_neon.rpm
	rm libmodbus*
}

INSANE_SKIP_${PN} = " \
    ldflags		\
    installed-vs-shipped \
"
FILES_${PN} = "/usr/lib/libmodbus.so.5 /usr/lib/libmodbus.so /data/lib/libmodbus.so.5.1.0"
