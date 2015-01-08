package edu.wpi.first.wpilibj.hal;

import java.io.File;
import java.nio.ByteBuffer;

//
// base class for all JNI wrappers
//
public class JNIWrapper
{
	static boolean libraryLoaded = false;
	static File jniLibrary = null;
	public static native ByteBuffer getPortWithModule(byte module, byte pin);
	public static native ByteBuffer getPort(byte pin);
}
