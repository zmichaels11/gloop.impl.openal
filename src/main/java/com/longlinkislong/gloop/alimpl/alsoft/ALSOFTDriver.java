/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.longlinkislong.gloop.alimpl.alsoft;

import com.longlinkislong.gloop.alspi.Driver;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.AL11;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALC10;
import org.lwjgl.openal.EXTEfx;
import org.lwjgl.system.MemoryStack;
import static org.lwjgl.system.MemoryUtil.NULL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author zmichaels
 */
final class ALSOFTDriver implements Driver<ALSOFTDevice, ALSOFTBuffer, ALSOFTListener, ALSOFTSource, ALSOFTAuxiliaryEffectSlot, ALSOFTEffect, ALSOFTFilter> {

    private static final Logger LOGGER = LoggerFactory.getLogger("ALSoftDriver");
    private final int maxAuxiliarySends = Integer.getInteger("com.longlinkislong.gloop.alsoftdriver.max_auxiliary_sends", 4);

    @Override
    public int sourceGetMaxAuxiliaryEffectSlotSends() {
        return this.maxAuxiliarySends;
    }

    @Override
    public ALSOFTAuxiliaryEffectSlot auxiliaryEffectSlotCreate() {
        final ALSOFTAuxiliaryEffectSlot effectSlot = new ALSOFTAuxiliaryEffectSlot();

        effectSlot.auxEffectSlotId = EXTEfx.alGenAuxiliaryEffectSlots();
        return effectSlot;
    }

    @Override
    public void auxiliaryEffectSlotDelete(final ALSOFTAuxiliaryEffectSlot slot) {
        if (slot.isValid()) {
            EXTEfx.alDeleteAuxiliaryEffectSlots(slot.auxEffectSlotId);
            slot.auxEffectSlotId = -1;
        }
    }

    @Override
    public void auxiliaryEffectSlotAttachEffect(
            final ALSOFTAuxiliaryEffectSlot slot,
            final ALSOFTEffect effect) {

        EXTEfx.alAuxiliaryEffectSloti(
                slot.auxEffectSlotId,
                EXTEfx.AL_EFFECTSLOT_EFFECT,
                effect.effectId);
    }

    @Override
    public void sourceSendAuxiliaryEffectSlot(
            ALSOFTSource source,
            ALSOFTAuxiliaryEffectSlot slot,
            int send) {

        AL11.alSource3i(
                source.sourceId,
                EXTEfx.AL_AUXILIARY_SEND_FILTER,
                slot.auxEffectSlotId,
                send,
                0);
    }

    @Override
    public void sourceSendAuxiliaryEffectSlot(ALSOFTSource source,
            ALSOFTAuxiliaryEffectSlot slot,
            int send,
            ALSOFTFilter filter) {

        AL11.alSource3i(
                source.sourceId,
                EXTEfx.AL_AUXILIARY_SEND_FILTER,
                slot.auxEffectSlotId,
                send,
                filter.filterId);
    }

    @Override
    public void sourceSendDisable(ALSOFTSource source, int send) {
        AL11.alSource3i(source.sourceId, EXTEfx.AL_AUXILIARY_SEND_FILTER, EXTEfx.AL_EFFECTSLOT_NULL, send, 0);
    }

    @Override
    public void sourceAttachDirectFilter(ALSOFTSource source, ALSOFTFilter filter) {
        AL10.alSourcei(source.sourceId, EXTEfx.AL_DIRECT_FILTER, filter.filterId);
    }

    @Override
    public void sourceRemoveDirectFilter(ALSOFTSource source) {
        AL10.alSourcei(source.sourceId, EXTEfx.AL_DIRECT_FILTER, EXTEfx.AL_FILTER_NULL);
    }

    @Override
    public ALSOFTEffect effectCreate(int effectType) {
        final ALSOFTEffect fx = new ALSOFTEffect();

        fx.effectId = EXTEfx.alGenEffects();
        EXTEfx.alEffecti(fx.effectId, EXTEfx.AL_EFFECT_TYPE, effectType);
        return fx;
    }

    @Override
    public void effectDelete(ALSOFTEffect effect) {
        if (effect.isValid()) {
            EXTEfx.alDeleteEffects(effect.effectId);
            effect.effectId = -1;
        }
    }

    @Override
    public void effectSetProperty(ALSOFTEffect effect, int name, int value) {
        EXTEfx.alEffecti(effect.effectId, name, value);
    }

    @Override
    public void effectSetProperty(ALSOFTEffect effect, int name, float value) {
        EXTEfx.alEffectf(effect.effectId, name, value);
    }

    @Override
    public ALSOFTFilter filterCreate(int type) {
        final ALSOFTFilter filter = new ALSOFTFilter();

        filter.filterId = EXTEfx.alGenFilters();
        EXTEfx.alFilteri(filter.filterId, EXTEfx.AL_FILTER_TYPE, type);
        return filter;
    }

    @Override
    public void filterDelete(ALSOFTFilter filter) {
        if (filter.isValid()) {
            EXTEfx.alDeleteFilters(filter.filterId);
            filter.filterId = -1;
        }
    }

    @Override
    public void filterSetProperty(ALSOFTFilter filter, int name, int value) {
        EXTEfx.alFilteri(filter.filterId, name, value);
    }

    @Override
    public void filterSetProperty(ALSOFTFilter filter, int name, float value) {
        EXTEfx.alFilterf(filter.filterId, name, value);
    }

    @Override
    public void distanceModelApply(int model) {
        AL10.alDistanceModel(model);
    }

    @Override
    public void sourceSetDistance(final ALSOFTSource src, final float relative, final float rolloff, final float max) {
        AL10.alSourcef(src.sourceId, AL10.AL_REFERENCE_DISTANCE, relative);
        AL10.alSourcef(src.sourceId, AL10.AL_MAX_DISTANCE, max);
        AL10.alSourcef(src.sourceId, AL10.AL_ROLLOFF_FACTOR, rolloff);
    }

    @Override
    public ALSOFTDevice deviceCreate() {
        final ALSOFTDevice device = new ALSOFTDevice();

        final String defaultDevice = ALC10.alcGetString(NULL, ALC10.ALC_DEFAULT_DEVICE_SPECIFIER);

        device.deviceId = ALC10.alcOpenDevice(defaultDevice);
        device.alcCaps = ALC.createCapabilities(device.deviceId);

        LOGGER.trace("Opened ALC device: [{}] handle: [{}]", defaultDevice, device.deviceId);

        try (MemoryStack mem = MemoryStack.stackPush()) {
            final IntBuffer attribs = mem.ints(EXTEfx.ALC_MAX_AUXILIARY_SENDS, this.maxAuxiliarySends, 0);

            device.contextId = ALC10.alcCreateContext(device.deviceId, attribs);

            if (!ALC10.alcMakeContextCurrent(device.contextId)) {
                LOGGER.error("Initializing OpenAL failed with error: {}", AL10.alGetError());
            } else {
                device.alCaps = AL.createCapabilities(device.alcCaps);
            }
        }

        return device;
    }

    @Override
    public void deviceDelete(ALSOFTDevice device) {
        if (device.isValid()) {
            ALC10.alcDestroyContext(device.contextId);
            device.contextId = -1;
            ALC10.alcCloseDevice(device.deviceId);
            device.deviceId = -1;
        }
    }

    private static final class Holder {

        private static final ALSOFTDriver INSTANCE = new ALSOFTDriver();

        private Holder() {
        }
    }

    public static ALSOFTDriver getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public ALSOFTBuffer bufferCreate() {
        final ALSOFTBuffer buffer = new ALSOFTBuffer();
        buffer.bufferId = AL10.alGenBuffers();
        return buffer;
    }

    @Override
    public void bufferDelete(ALSOFTBuffer buffer) {
        if (buffer.isValid()) {
            AL10.alDeleteBuffers(buffer.bufferId);
            buffer.bufferId = -1;
        }
    }

    @Override
    public void bufferSetData(ALSOFTBuffer buffer, int format, ByteBuffer data, int frequency) {
        AL10.alBufferData(buffer.bufferId, format, data, frequency);
    }

    @Override
    public void bufferSetData(ALSOFTBuffer buffer, int format, short[] data, int frequency) {
        AL10.alBufferData(buffer.bufferId, format, data, frequency);
    }

    @Override
    public void bufferSetData(ALSOFTBuffer buffer, int format, int[] data, int frequency) {
        AL10.alBufferData(buffer.bufferId, format, data, frequency);
    }

    @Override
    public void bufferSetData(ALSOFTBuffer buffer, int format, float[] data, int frequency) {
        AL10.alBufferData(buffer.bufferId, format, data, frequency);
    }

    @Override
    public ALSOFTListener listenerGetInstance() {
        return ALSOFTListener.Holder.INSTANCE;
    }

    @Override
    public void listenerSetGain(ALSOFTListener listener, float gain) {
        AL10.alListenerf(AL10.AL_GAIN, gain);
    }

    @Override
    public void listenerSetOrientation(ALSOFTListener listener, float atX, float atY, float atZ, float upX, float upY, float upZ) {
        try (MemoryStack mem = MemoryStack.stackPush()) {
            final FloatBuffer values = mem.floats(atX, atY, atZ, upX, upY, upZ);

            AL10.alListenerfv(AL10.AL_ORIENTATION, values);
        }
    }

    @Override
    public void listenerSetPosition(ALSOFTListener listener, float x, float y, float z) {
        AL10.alListener3f(AL10.AL_POSITION, x, y, z);
    }

    @Override
    public void listenerSetVelocity(ALSOFTListener listener, float x, float y, float z) {
        AL10.alListener3f(AL10.AL_VELOCITY, x, y, z);
    }

    @Override
    public ALSOFTSource sourceCreate() {
        ALSOFTSource source = new ALSOFTSource();
        source.sourceId = AL10.alGenSources();
        return source;
    }

    @Override
    public void sourceDelete(ALSOFTSource source) {
        if (source.isValid()) {
            AL10.alDeleteSources(source.sourceId);
            source.sourceId = -1;
        }
    }

    @Override
    public ALSOFTBuffer sourceDequeueBuffer(ALSOFTSource source) {
        final ALSOFTBuffer buffer = new ALSOFTBuffer();
        buffer.bufferId = AL10.alSourceUnqueueBuffers(source.sourceId);
        return buffer;
    }

    @Override
    public void sourceEnqueueBuffer(ALSOFTSource source, ALSOFTBuffer buffer) {
        AL10.alSourceQueueBuffers(source.sourceId, buffer.bufferId);
    }

    @Override
    public int sourceGetBuffersProcessed(ALSOFTSource source) {
        return AL10.alGetSourcei(source.sourceId, AL10.AL_BUFFERS_PROCESSED);
    }

    @Override
    public int sourceGetBuffersQueued(ALSOFTSource source) {
        return AL10.alGetSourcei(source.sourceId, AL10.AL_BUFFERS_QUEUED);
    }

    @Override
    public void sourceSetDirection(ALSOFTSource source, float x, float y, float z) {
        AL10.alSource3f(source.sourceId, AL10.AL_DIRECTION, x, y, z);
    }

    @Override
    public void sourceSetGain(ALSOFTSource source, float gain) {
        AL10.alSourcef(source.sourceId, AL10.AL_GAIN, gain);
    }

    @Override
    public void sourceSetPitch(ALSOFTSource source, float pitch) {
        AL10.alSourcef(source.sourceId, AL10.AL_PITCH, pitch);
    }

    @Override
    public void sourceSetPosition(ALSOFTSource source, float x, float y, float z) {
        try (MemoryStack mem = MemoryStack.stackPush()) {
            final FloatBuffer values = mem.floats(x, y, z);

            AL10.alSourcefv(source.sourceId, AL10.AL_POSITION, values);
        }
    }

    @Override
    public void sourceSetVelocity(ALSOFTSource source, float x, float y, float z) {
        AL10.alSource3f(source.sourceId, AL10.AL_VELOCITY, x, y, z);
    }

    @Override
    public void sourcePlay(ALSOFTSource source) {
        AL10.alSourcePlay(source.sourceId);
    }

    @Override
    public void sourceSetBuffer(ALSOFTSource source, ALSOFTBuffer buffer) {
        AL10.alSourcei(source.sourceId, AL10.AL_BUFFER, buffer.bufferId);
    }

    @Override
    public void sourceSetLooping(ALSOFTSource source, boolean loop) {
        AL10.alSourcei(source.sourceId, AL10.AL_LOOPING, loop ? AL10.AL_TRUE : AL10.AL_FALSE);
    }

    @Override
    public void sourceSetCone(final ALSOFTSource src, final float innerAngle, final float outerAngle, final float outerGain) {
        AL10.alSourcef(src.sourceId, AL10.AL_CONE_INNER_ANGLE, innerAngle);
        AL10.alSourcef(src.sourceId, AL10.AL_CONE_OUTER_ANGLE, outerAngle);
        AL10.alSourcef(src.sourceId, AL10.AL_CONE_OUTER_GAIN, outerGain);
    }

    public int sourceGetState(final ALSOFTSource src) {
        return AL10.alGetSourcei(src.sourceId, AL10.AL_SOURCE_STATE);
    }
}
