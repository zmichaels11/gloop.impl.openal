/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.longlinkislong.gloop.alimpl.al10;

import com.longlinkislong.gloop.alspi.Driver;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.ALContext;
import org.lwjgl.openal.ALDevice;
import org.lwjgl.system.MemoryUtil;

/**
 *
 * @author zmichaels
 */
final class AL10Driver implements Driver<AL10Device, AL10Buffer, AL10Listener, AL10Source,
        AL10AuxiliaryEffectSlot, AL10Effect, AL10Filter> {

    @Override
    public void distanceModelApply(int model) {
        AL10.alDistanceModel(model);
    }
        
    @Override
    public void sourceSetDistance(final AL10Source src, final float relative, final float rolloff, final float max) {
        AL10.alSourcef(src.sourceId, AL10.AL_REFERENCE_DISTANCE, relative);
        AL10.alSourcef(src.sourceId, AL10.AL_MAX_DISTANCE, max);
        AL10.alSourcef(src.sourceId, AL10.AL_ROLLOFF_FACTOR, rolloff);
    }
    
    @Override
    public AL10Device deviceCreate() {
        final AL10Device device = new AL10Device();

        device.handle = ALDevice.create();
        device.caps = device.handle.getCapabilities();
        device.context = ALContext.create(device.handle);

        return device;
    }

    @Override
    public void deviceDelete(AL10Device device) {
        if (device.isValid()) {
            device.context.destroy();
            device.context = null;
            device.caps = null;
            device.handle.destroy();            
            device.handle = null;
        }
    }

    private static final class Holder {

        private static final AL10Driver INSTANCE = new AL10Driver();
    }

    public static AL10Driver getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public AL10Buffer bufferCreate() {
        final AL10Buffer buffer = new AL10Buffer();
        buffer.bufferId = AL10.alGenBuffers();
        return buffer;
    }

    @Override
    public void bufferDelete(AL10Buffer buffer) {
        if (buffer.isValid()) {
            AL10.alDeleteBuffers(buffer.bufferId);
            buffer.bufferId = -1;
        }
    }

    @Override
    public void bufferSetData(AL10Buffer buffer, int format, ByteBuffer data, int frequency) {
        AL10.alBufferData(buffer.bufferId, format, data, frequency);
    }

    @Override
    public AL10Listener listenerGetInstance() {
        return AL10Listener.Holder.INSTANCE;
    }

    @Override
    public void listenerSetGain(AL10Listener listener, float gain) {
        AL10.alListenerf(AL10.AL_GAIN, gain);
    }

    @Override
    public void listenerSetOrientation(AL10Listener listener, float atX, float atY, float atZ, float upX, float upY, float upZ) {
        final FloatBuffer values = MemoryUtil.memAllocFloat(6);

        values.put(0, atX);
        values.put(1, atY);
        values.put(2, atZ);
        values.put(3, upX);
        values.put(4, upY);
        values.put(5, upZ);

        AL10.alListenerfv(AL10.AL_ORIENTATION, values);

        MemoryUtil.memFree(values);
    }

    @Override
    public void listenerSetPosition(AL10Listener listener, float x, float y, float z) {
        AL10.alListener3f(AL10.AL_POSITION, x, y, z);
    }

    @Override
    public void listenerSetVelocity(AL10Listener listener, float x, float y, float z) {
        AL10.alListener3f(AL10.AL_VELOCITY, x, y, z);
    }

    @Override
    public AL10Source sourceCreate() {
        AL10Source source = new AL10Source();
        source.sourceId = AL10.alGenSources();
        return source;
    }

    @Override
    public void sourceDelete(AL10Source source) {
        if (source.isValid()) {
            AL10.alDeleteSources(source.sourceId);
            source.sourceId = -1;
        }
    }

    @Override
    public AL10Buffer sourceDequeueBuffer(AL10Source source) {
        final AL10Buffer buffer = new AL10Buffer();
        buffer.bufferId = AL10.alSourceUnqueueBuffers(source.sourceId);
        return buffer;
    }

    @Override
    public void sourceEnqueueBuffer(AL10Source source, AL10Buffer buffer) {
        AL10.alSourceQueueBuffers(source.sourceId, buffer.bufferId);
    }

    @Override
    public int sourceGetBuffersProcessed(AL10Source source) {
        return AL10.alGetSourcei(source.sourceId, AL10.AL_BUFFERS_PROCESSED);
    }

    @Override
    public int sourceGetBuffersQueued(AL10Source source) {
        return AL10.alGetSourcei(source.sourceId, AL10.AL_BUFFERS_QUEUED);
    }

    @Override
    public void sourceSetDirection(AL10Source source, float x, float y, float z) {
        AL10.alSource3f(source.sourceId, AL10.AL_DIRECTION, x, y, z);
    }

    @Override
    public void sourceSetGain(AL10Source source, float gain) {
        AL10.alSourcef(source.sourceId, AL10.AL_GAIN, gain);
    }

    @Override
    public void sourceSetPitch(AL10Source source, float pitch) {
        AL10.alSourcef(source.sourceId, AL10.AL_PITCH, pitch);
    }

    @Override
    public void sourceSetPosition(AL10Source source, float x, float y, float z) {
        final FloatBuffer values = MemoryUtil.memAllocFloat(3);

        values.put(0, x);
        values.put(1, y);
        values.put(2, z);

        //AL10.alSource3f(source.sourceId, AL10.AL_POSITION, x, y, z);
        AL10.alSourcefv(source.sourceId, AL10.AL_POSITION, values);
        MemoryUtil.memFree(values);
    }

    @Override
    public void sourceSetVelocity(AL10Source source, float x, float y, float z) {
        AL10.alSource3f(source.sourceId, AL10.AL_VELOCITY, x, y, z);
    }

    @Override
    public void sourcePlay(AL10Source source) {
        AL10.alSourcePlay(source.sourceId);
    }   

    @Override
    public void sourceSetBuffer(AL10Source source, AL10Buffer buffer) {
        AL10.alSourcei(source.sourceId, AL10.AL_BUFFER, buffer.bufferId);
    }

    @Override
    public void sourceSetLooping(AL10Source source, boolean loop) {
        AL10.alSourcei(source.sourceId, AL10.AL_LOOPING, loop ? AL10.AL_TRUE : AL10.AL_FALSE);
    }
    
    @Override
    public void sourceSetCone(final AL10Source src, final float innerAngle, final float outerAngle, final float outerGain) {
        AL10.alSourcef(src.sourceId, AL10.AL_CONE_INNER_ANGLE, innerAngle);
        AL10.alSourcef(src.sourceId, AL10.AL_CONE_OUTER_ANGLE, outerAngle);
        AL10.alSourcef(src.sourceId, AL10.AL_CONE_OUTER_GAIN, outerGain);
    }        
}
