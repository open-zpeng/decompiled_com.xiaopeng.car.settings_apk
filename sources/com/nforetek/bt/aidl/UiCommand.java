package com.nforetek.bt.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.RemoteException;
import com.nforetek.bt.aidl.UiCallbackA2dp;
import com.nforetek.bt.aidl.UiCallbackAvrcp;
import com.nforetek.bt.aidl.UiCallbackBluetooth;
import com.nforetek.bt.aidl.UiCallbackGattServer;
import com.nforetek.bt.aidl.UiCallbackHfp;
import com.nforetek.bt.aidl.UiCallbackHid;
import com.nforetek.bt.aidl.UiCallbackMap;
import com.nforetek.bt.aidl.UiCallbackOpp;
import com.nforetek.bt.aidl.UiCallbackPbap;
import com.nforetek.bt.aidl.UiCallbackSpp;
import com.nforetek.bt.aidl.UiCallbackUsbBluetooth;
import java.util.List;
/* loaded from: classes.dex */
public interface UiCommand extends IInterface {
    boolean cancelBtDiscovery() throws RemoteException;

    String getA2dpConnectedAddress() throws RemoteException;

    int getA2dpConnectionState() throws RemoteException;

    int getA2dpStreamType() throws RemoteException;

    String getAvrcpConnectedAddress() throws RemoteException;

    int getAvrcpConnectionState() throws RemoteException;

    int getBtAutoConnectCondition() throws RemoteException;

    int getBtAutoConnectPeriod() throws RemoteException;

    int getBtAutoConnectState() throws RemoteException;

    String getBtAutoConnectingAddress() throws RemoteException;

    String getBtLocalAddress() throws RemoteException;

    String getBtLocalName() throws RemoteException;

    String getBtRemoteDeviceName(String str) throws RemoteException;

    int getBtRemoteUuids(String str) throws RemoteException;

    int getBtRoleMode() throws RemoteException;

    int getBtState() throws RemoteException;

    List<ParcelUuid> getGattAddedGattCharacteristicUuidList(ParcelUuid parcelUuid) throws RemoteException;

    List<ParcelUuid> getGattAddedGattDescriptorUuidList(ParcelUuid parcelUuid, ParcelUuid parcelUuid2) throws RemoteException;

    List<ParcelUuid> getGattAddedGattServiceUuidList() throws RemoteException;

    int getGattServerConnectionState() throws RemoteException;

    int getHfpAudioConnectionState() throws RemoteException;

    List<NfHfpClientCall> getHfpCallList() throws RemoteException;

    String getHfpConnectedAddress() throws RemoteException;

    int getHfpConnectionState() throws RemoteException;

    int getHfpRemoteBatteryIndicator() throws RemoteException;

    String getHfpRemoteNetworkOperator() throws RemoteException;

    int getHfpRemoteSignalStrength() throws RemoteException;

    String getHfpRemoteSubscriberNumber() throws RemoteException;

    String getHidConnectedAddress() throws RemoteException;

    int getHidConnectionState() throws RemoteException;

    int getLocalUsbConnectionState() throws RemoteException;

    int getMapCurrentState(String str) throws RemoteException;

    int getMapRegisterState(String str) throws RemoteException;

    String getNfServiceVersionName() throws RemoteException;

    String getOppFilePath() throws RemoteException;

    int getPbapConnectionState() throws RemoteException;

    String getPbapDownloadingAddress() throws RemoteException;

    int getProfileConnectionState(int i) throws RemoteException;

    String getTargetAddress() throws RemoteException;

    String getUiServiceVersionName() throws RemoteException;

    String getUsbAddress() throws RemoteException;

    String getUsbBtLocalName() throws RemoteException;

    String getUsbConnectedDevice() throws RemoteException;

    int getUsbConnectionState(String str) throws RemoteException;

    boolean isA2dpConnected() throws RemoteException;

    boolean isA2dpServiceReady() throws RemoteException;

    boolean isAvrcp13Supported(String str) throws RemoteException;

    boolean isAvrcp14BrowsingChannelEstablished() throws RemoteException;

    boolean isAvrcp14Supported(String str) throws RemoteException;

    boolean isAvrcpConnected() throws RemoteException;

    boolean isAvrcpServiceReady() throws RemoteException;

    boolean isBluetoothServiceReady() throws RemoteException;

    boolean isBtAutoConnectEnable() throws RemoteException;

    boolean isBtDiscoverable() throws RemoteException;

    boolean isBtDiscovering() throws RemoteException;

    boolean isBtEnabled() throws RemoteException;

    boolean isGattServiceReady() throws RemoteException;

    boolean isHfpConnected() throws RemoteException;

    boolean isHfpInBandRingtoneSupport() throws RemoteException;

    boolean isHfpMicMute() throws RemoteException;

    boolean isHfpRemoteOnRoaming() throws RemoteException;

    boolean isHfpRemoteTelecomServiceOn() throws RemoteException;

    boolean isHfpRemoteVoiceDialOn() throws RemoteException;

    boolean isHfpServiceReady() throws RemoteException;

    boolean isHidConnected() throws RemoteException;

    boolean isHidServiceReady() throws RemoteException;

    boolean isMapNotificationRegistered(String str) throws RemoteException;

    boolean isMapServiceReady() throws RemoteException;

    boolean isOppServiceReady() throws RemoteException;

    boolean isPbapDownloading() throws RemoteException;

    boolean isPbapServiceReady() throws RemoteException;

    boolean isSppConnected(String str) throws RemoteException;

    boolean isSppServiceReady() throws RemoteException;

    boolean isUsbA2dpConnected(String str) throws RemoteException;

    boolean isUsbAvrcpConnected(String str) throws RemoteException;

    boolean isUsbDiscovering() throws RemoteException;

    boolean isUsbEnabled() throws RemoteException;

    boolean isUsbHfpConnected(String str) throws RemoteException;

    void muteHfpMic(boolean z) throws RemoteException;

    void pauseA2dpRender() throws RemoteException;

    void pauseHfpRender() throws RemoteException;

    boolean registerA2dpCallback(UiCallbackA2dp uiCallbackA2dp) throws RemoteException;

    boolean registerAvrcpCallback(UiCallbackAvrcp uiCallbackAvrcp) throws RemoteException;

    boolean registerBtCallback(UiCallbackBluetooth uiCallbackBluetooth) throws RemoteException;

    boolean registerGattServerCallback(UiCallbackGattServer uiCallbackGattServer) throws RemoteException;

    boolean registerHfpCallback(UiCallbackHfp uiCallbackHfp) throws RemoteException;

    boolean registerHidCallback(UiCallbackHid uiCallbackHid) throws RemoteException;

    boolean registerMapCallback(UiCallbackMap uiCallbackMap) throws RemoteException;

    boolean registerOppCallback(UiCallbackOpp uiCallbackOpp) throws RemoteException;

    boolean registerPbapCallback(UiCallbackPbap uiCallbackPbap) throws RemoteException;

    boolean registerSppCallback(UiCallbackSpp uiCallbackSpp) throws RemoteException;

    boolean registerUsbCallback(UiCallbackUsbBluetooth uiCallbackUsbBluetooth) throws RemoteException;

    boolean reqA2dpConnect(String str) throws RemoteException;

    boolean reqA2dpDisconnect(String str) throws RemoteException;

    boolean reqAvrcp13GetCapabilitiesSupportEvent() throws RemoteException;

    boolean reqAvrcp13GetElementAttributesPlaying() throws RemoteException;

    boolean reqAvrcp13GetPlayStatus() throws RemoteException;

    boolean reqAvrcp13GetPlayerSettingAttributesList() throws RemoteException;

    boolean reqAvrcp13GetPlayerSettingCurrentValues() throws RemoteException;

    boolean reqAvrcp13GetPlayerSettingValuesList(byte b) throws RemoteException;

    boolean reqAvrcp13NextGroup() throws RemoteException;

    boolean reqAvrcp13PreviousGroup() throws RemoteException;

    boolean reqAvrcp13SetPlayerSettingValue(byte b, byte b2) throws RemoteException;

    boolean reqAvrcp14AddToNowPlaying(byte b, int i, long j) throws RemoteException;

    boolean reqAvrcp14ChangePath(int i, long j, byte b) throws RemoteException;

    boolean reqAvrcp14GetFolderItems(byte b) throws RemoteException;

    boolean reqAvrcp14GetItemAttributes(byte b, int i, long j) throws RemoteException;

    boolean reqAvrcp14PlaySelectedItem(byte b, int i, long j) throws RemoteException;

    boolean reqAvrcp14Search(String str) throws RemoteException;

    boolean reqAvrcp14SetAbsoluteVolume(byte b) throws RemoteException;

    boolean reqAvrcp14SetAddressedPlayer(int i) throws RemoteException;

    boolean reqAvrcp14SetBrowsedPlayer(int i) throws RemoteException;

    boolean reqAvrcpBackward() throws RemoteException;

    boolean reqAvrcpConnect(String str) throws RemoteException;

    boolean reqAvrcpDisconnect(String str) throws RemoteException;

    boolean reqAvrcpForward() throws RemoteException;

    boolean reqAvrcpPause() throws RemoteException;

    boolean reqAvrcpPlay() throws RemoteException;

    boolean reqAvrcpRegisterEventWatcher(byte b, long j) throws RemoteException;

    boolean reqAvrcpStartFastForward() throws RemoteException;

    boolean reqAvrcpStartRewind() throws RemoteException;

    boolean reqAvrcpStop() throws RemoteException;

    boolean reqAvrcpStopFastForward() throws RemoteException;

    boolean reqAvrcpStopRewind() throws RemoteException;

    boolean reqAvrcpUnregisterEventWatcher(byte b) throws RemoteException;

    void reqAvrcpUpdateSongStatus() throws RemoteException;

    boolean reqAvrcpVolumeDown() throws RemoteException;

    boolean reqAvrcpVolumeUp() throws RemoteException;

    int reqBtConnectHfpA2dp(String str) throws RemoteException;

    int reqBtDisconnectAll() throws RemoteException;

    boolean reqBtPair(String str) throws RemoteException;

    boolean reqBtPairedDevices() throws RemoteException;

    boolean reqBtUnpair(String str) throws RemoteException;

    boolean reqGattServerAddCharacteristic(ParcelUuid parcelUuid, int i, int i2) throws RemoteException;

    boolean reqGattServerAddDescriptor(ParcelUuid parcelUuid, int i) throws RemoteException;

    boolean reqGattServerBeginServiceDeclaration(int i, ParcelUuid parcelUuid) throws RemoteException;

    boolean reqGattServerClearServices() throws RemoteException;

    boolean reqGattServerDisconnect(String str) throws RemoteException;

    boolean reqGattServerEndServiceDeclaration() throws RemoteException;

    boolean reqGattServerListen(boolean z) throws RemoteException;

    boolean reqGattServerRemoveService(int i, ParcelUuid parcelUuid) throws RemoteException;

    boolean reqGattServerSendNotification(String str, int i, ParcelUuid parcelUuid, ParcelUuid parcelUuid2, boolean z, byte[] bArr) throws RemoteException;

    boolean reqGattServerSendResponse(String str, int i, int i2, int i3, byte[] bArr) throws RemoteException;

    boolean reqHfpAnswerCall(int i) throws RemoteException;

    boolean reqHfpAudioTransferToCarkit() throws RemoteException;

    boolean reqHfpAudioTransferToPhone() throws RemoteException;

    boolean reqHfpConnect(String str) throws RemoteException;

    boolean reqHfpDialCall(String str) throws RemoteException;

    boolean reqHfpDisconnect(String str) throws RemoteException;

    boolean reqHfpMemoryDial(String str) throws RemoteException;

    boolean reqHfpReDial() throws RemoteException;

    boolean reqHfpRejectIncomingCall() throws RemoteException;

    boolean reqHfpSendDtmf(String str) throws RemoteException;

    boolean reqHfpTerminateCurrentCall() throws RemoteException;

    boolean reqHfpVoiceDial(boolean z) throws RemoteException;

    boolean reqHidConnect(String str) throws RemoteException;

    boolean reqHidDisconnect(String str) throws RemoteException;

    boolean reqMapChangeReadStatus(String str, int i, String str2, boolean z) throws RemoteException;

    void reqMapCleanDatabase() throws RemoteException;

    void reqMapDatabaseAvailable() throws RemoteException;

    void reqMapDeleteDatabaseByAddress(String str) throws RemoteException;

    boolean reqMapDeleteMessage(String str, int i, String str2) throws RemoteException;

    boolean reqMapDownloadInterrupt(String str) throws RemoteException;

    boolean reqMapDownloadMessage(String str, int i, boolean z, int i2, int i3, int i4, String str2, String str3, String str4, String str5, int i5, int i6) throws RemoteException;

    boolean reqMapDownloadSingleMessage(String str, int i, String str2, int i2) throws RemoteException;

    boolean reqMapRegisterNotification(String str, boolean z) throws RemoteException;

    boolean reqMapSendMessage(String str, String str2, String str3) throws RemoteException;

    void reqMapUnregisterNotification(String str) throws RemoteException;

    boolean reqOppAcceptReceiveFile(boolean z) throws RemoteException;

    boolean reqOppInterruptReceiveFile() throws RemoteException;

    void reqPbapCleanDatabase() throws RemoteException;

    void reqPbapDatabaseAvailable(String str) throws RemoteException;

    void reqPbapDatabaseQueryNameByNumber(String str, String str2) throws RemoteException;

    void reqPbapDatabaseQueryNameByPartialNumber(String str, String str2, int i) throws RemoteException;

    void reqPbapDeleteDatabaseByAddress(String str) throws RemoteException;

    boolean reqPbapDownload(String str, int i, int i2) throws RemoteException;

    boolean reqPbapDownloadInterrupt(String str) throws RemoteException;

    boolean reqPbapDownloadRange(String str, int i, int i2, int i3, int i4) throws RemoteException;

    boolean reqPbapDownloadRangeToContactsProvider(String str, int i, int i2, int i3, int i4) throws RemoteException;

    boolean reqPbapDownloadRangeToDatabase(String str, int i, int i2, int i3, int i4) throws RemoteException;

    boolean reqPbapDownloadToContactsProvider(String str, int i, int i2) throws RemoteException;

    boolean reqPbapDownloadToDatabase(String str, int i, int i2) throws RemoteException;

    boolean reqSendHidMouseCommand(int i, int i2, int i3, int i4) throws RemoteException;

    boolean reqSendHidVirtualKeyCommand(int i, int i2) throws RemoteException;

    boolean reqSppConnect(String str) throws RemoteException;

    void reqSppConnectedDeviceAddressList() throws RemoteException;

    boolean reqSppDisconnect(String str) throws RemoteException;

    void reqSppSendData(String str, byte[] bArr) throws RemoteException;

    List<UsbBluetoothDevice> reqUsbBtPairedDevices() throws RemoteException;

    boolean setA2dpLocalVolume(float f) throws RemoteException;

    boolean setA2dpStreamType(int i) throws RemoteException;

    void setBtAutoConnect(int i, int i2) throws RemoteException;

    boolean setBtDiscoverableTimeout(int i) throws RemoteException;

    boolean setBtEnable(boolean z) throws RemoteException;

    boolean setBtLocalName(String str) throws RemoteException;

    boolean setMapDownloadNotify(int i) throws RemoteException;

    boolean setOppFilePath(String str) throws RemoteException;

    boolean setPbapDownloadNotify(int i) throws RemoteException;

    void setTargetAddress(String str) throws RemoteException;

    boolean setUsbBtLocalName(String str) throws RemoteException;

    boolean setUsbEnabled(boolean z) throws RemoteException;

    void startA2dpRender() throws RemoteException;

    boolean startBtDiscovery() throws RemoteException;

    void startHfpRender() throws RemoteException;

    boolean switchBtRoleMode(int i) throws RemoteException;

    boolean unregisterA2dpCallback(UiCallbackA2dp uiCallbackA2dp) throws RemoteException;

    boolean unregisterAvrcpCallback(UiCallbackAvrcp uiCallbackAvrcp) throws RemoteException;

    boolean unregisterBtCallback(UiCallbackBluetooth uiCallbackBluetooth) throws RemoteException;

    boolean unregisterGattServerCallback(UiCallbackGattServer uiCallbackGattServer) throws RemoteException;

    boolean unregisterHfpCallback(UiCallbackHfp uiCallbackHfp) throws RemoteException;

    boolean unregisterHidCallback(UiCallbackHid uiCallbackHid) throws RemoteException;

    boolean unregisterMapCallback(UiCallbackMap uiCallbackMap) throws RemoteException;

    boolean unregisterOppCallback(UiCallbackOpp uiCallbackOpp) throws RemoteException;

    boolean unregisterPbapCallback(UiCallbackPbap uiCallbackPbap) throws RemoteException;

    boolean unregisterSppCallback(UiCallbackSpp uiCallbackSpp) throws RemoteException;

    boolean unregisterUsbCallback(UiCallbackUsbBluetooth uiCallbackUsbBluetooth) throws RemoteException;

    boolean usbConnect(String str) throws RemoteException;

    boolean usbDisConnect() throws RemoteException;

    boolean usbDisConnectDevice(String str) throws RemoteException;

    boolean usbReqUnpair(String str) throws RemoteException;

    boolean usbSearch() throws RemoteException;

    boolean usbStopSearch() throws RemoteException;

    /* loaded from: classes.dex */
    public static abstract class Stub extends Binder implements UiCommand {
        private static final String DESCRIPTOR = "com.nforetek.bt.aidl.UiCommand";
        static final int TRANSACTION_cancelBtDiscovery = 70;
        static final int TRANSACTION_getA2dpConnectedAddress = 15;
        static final int TRANSACTION_getA2dpConnectionState = 13;
        static final int TRANSACTION_getA2dpStreamType = 22;
        static final int TRANSACTION_getAvrcpConnectedAddress = 27;
        static final int TRANSACTION_getAvrcpConnectionState = 25;
        static final int TRANSACTION_getBtAutoConnectCondition = 89;
        static final int TRANSACTION_getBtAutoConnectPeriod = 90;
        static final int TRANSACTION_getBtAutoConnectState = 91;
        static final int TRANSACTION_getBtAutoConnectingAddress = 92;
        static final int TRANSACTION_getBtLocalAddress = 76;
        static final int TRANSACTION_getBtLocalName = 74;
        static final int TRANSACTION_getBtRemoteDeviceName = 75;
        static final int TRANSACTION_getBtRemoteUuids = 85;
        static final int TRANSACTION_getBtRoleMode = 87;
        static final int TRANSACTION_getBtState = 79;
        static final int TRANSACTION_getGattAddedGattCharacteristicUuidList = 199;
        static final int TRANSACTION_getGattAddedGattDescriptorUuidList = 200;
        static final int TRANSACTION_getGattAddedGattServiceUuidList = 198;
        static final int TRANSACTION_getGattServerConnectionState = 187;
        static final int TRANSACTION_getHfpAudioConnectionState = 98;
        static final int TRANSACTION_getHfpCallList = 102;
        static final int TRANSACTION_getHfpConnectedAddress = 97;
        static final int TRANSACTION_getHfpConnectionState = 95;
        static final int TRANSACTION_getHfpRemoteBatteryIndicator = 104;
        static final int TRANSACTION_getHfpRemoteNetworkOperator = 116;
        static final int TRANSACTION_getHfpRemoteSignalStrength = 101;
        static final int TRANSACTION_getHfpRemoteSubscriberNumber = 117;
        static final int TRANSACTION_getHidConnectedAddress = 153;
        static final int TRANSACTION_getHidConnectionState = 151;
        static final int TRANSACTION_getLocalUsbConnectionState = 220;
        static final int TRANSACTION_getMapCurrentState = 169;
        static final int TRANSACTION_getMapRegisterState = 170;
        static final int TRANSACTION_getNfServiceVersionName = 66;
        static final int TRANSACTION_getOppFilePath = 178;
        static final int TRANSACTION_getPbapConnectionState = 126;
        static final int TRANSACTION_getPbapDownloadingAddress = 128;
        static final int TRANSACTION_getProfileConnectionState = 221;
        static final int TRANSACTION_getTargetAddress = 182;
        static final int TRANSACTION_getUiServiceVersionName = 1;
        static final int TRANSACTION_getUsbAddress = 218;
        static final int TRANSACTION_getUsbBtLocalName = 216;
        static final int TRANSACTION_getUsbConnectedDevice = 219;
        static final int TRANSACTION_getUsbConnectionState = 213;
        static final int TRANSACTION_isA2dpConnected = 14;
        static final int TRANSACTION_isA2dpServiceReady = 3;
        static final int TRANSACTION_isAvrcp13Supported = 30;
        static final int TRANSACTION_isAvrcp14BrowsingChannelEstablished = 54;
        static final int TRANSACTION_isAvrcp14Supported = 31;
        static final int TRANSACTION_isAvrcpConnected = 26;
        static final int TRANSACTION_isAvrcpServiceReady = 2;
        static final int TRANSACTION_isBluetoothServiceReady = 5;
        static final int TRANSACTION_isBtAutoConnectEnable = 82;
        static final int TRANSACTION_isBtDiscoverable = 81;
        static final int TRANSACTION_isBtDiscovering = 80;
        static final int TRANSACTION_isBtEnabled = 78;
        static final int TRANSACTION_isGattServiceReady = 184;
        static final int TRANSACTION_isHfpConnected = 96;
        static final int TRANSACTION_isHfpInBandRingtoneSupport = 123;
        static final int TRANSACTION_isHfpMicMute = 121;
        static final int TRANSACTION_isHfpRemoteOnRoaming = 103;
        static final int TRANSACTION_isHfpRemoteTelecomServiceOn = 105;
        static final int TRANSACTION_isHfpRemoteVoiceDialOn = 106;
        static final int TRANSACTION_isHfpServiceReady = 6;
        static final int TRANSACTION_isHidConnected = 152;
        static final int TRANSACTION_isHidServiceReady = 7;
        static final int TRANSACTION_isMapNotificationRegistered = 164;
        static final int TRANSACTION_isMapServiceReady = 10;
        static final int TRANSACTION_isOppServiceReady = 9;
        static final int TRANSACTION_isPbapDownloading = 127;
        static final int TRANSACTION_isPbapServiceReady = 8;
        static final int TRANSACTION_isSppConnected = 147;
        static final int TRANSACTION_isSppServiceReady = 4;
        static final int TRANSACTION_isUsbA2dpConnected = 212;
        static final int TRANSACTION_isUsbAvrcpConnected = 214;
        static final int TRANSACTION_isUsbDiscovering = 210;
        static final int TRANSACTION_isUsbEnabled = 209;
        static final int TRANSACTION_isUsbHfpConnected = 211;
        static final int TRANSACTION_muteHfpMic = 122;
        static final int TRANSACTION_pauseA2dpRender = 18;
        static final int TRANSACTION_pauseHfpRender = 119;
        static final int TRANSACTION_registerA2dpCallback = 11;
        static final int TRANSACTION_registerAvrcpCallback = 23;
        static final int TRANSACTION_registerBtCallback = 64;
        static final int TRANSACTION_registerGattServerCallback = 185;
        static final int TRANSACTION_registerHfpCallback = 93;
        static final int TRANSACTION_registerHidCallback = 149;
        static final int TRANSACTION_registerMapCallback = 158;
        static final int TRANSACTION_registerOppCallback = 175;
        static final int TRANSACTION_registerPbapCallback = 124;
        static final int TRANSACTION_registerSppCallback = 142;
        static final int TRANSACTION_registerUsbCallback = 201;
        static final int TRANSACTION_reqA2dpConnect = 16;
        static final int TRANSACTION_reqA2dpDisconnect = 17;
        static final int TRANSACTION_reqAvrcp13GetCapabilitiesSupportEvent = 43;
        static final int TRANSACTION_reqAvrcp13GetElementAttributesPlaying = 48;
        static final int TRANSACTION_reqAvrcp13GetPlayStatus = 49;
        static final int TRANSACTION_reqAvrcp13GetPlayerSettingAttributesList = 44;
        static final int TRANSACTION_reqAvrcp13GetPlayerSettingCurrentValues = 46;
        static final int TRANSACTION_reqAvrcp13GetPlayerSettingValuesList = 45;
        static final int TRANSACTION_reqAvrcp13NextGroup = 52;
        static final int TRANSACTION_reqAvrcp13PreviousGroup = 53;
        static final int TRANSACTION_reqAvrcp13SetPlayerSettingValue = 47;
        static final int TRANSACTION_reqAvrcp14AddToNowPlaying = 62;
        static final int TRANSACTION_reqAvrcp14ChangePath = 58;
        static final int TRANSACTION_reqAvrcp14GetFolderItems = 57;
        static final int TRANSACTION_reqAvrcp14GetItemAttributes = 59;
        static final int TRANSACTION_reqAvrcp14PlaySelectedItem = 60;
        static final int TRANSACTION_reqAvrcp14Search = 61;
        static final int TRANSACTION_reqAvrcp14SetAbsoluteVolume = 63;
        static final int TRANSACTION_reqAvrcp14SetAddressedPlayer = 55;
        static final int TRANSACTION_reqAvrcp14SetBrowsedPlayer = 56;
        static final int TRANSACTION_reqAvrcpBackward = 36;
        static final int TRANSACTION_reqAvrcpConnect = 28;
        static final int TRANSACTION_reqAvrcpDisconnect = 29;
        static final int TRANSACTION_reqAvrcpForward = 35;
        static final int TRANSACTION_reqAvrcpPause = 34;
        static final int TRANSACTION_reqAvrcpPlay = 32;
        static final int TRANSACTION_reqAvrcpRegisterEventWatcher = 50;
        static final int TRANSACTION_reqAvrcpStartFastForward = 39;
        static final int TRANSACTION_reqAvrcpStartRewind = 41;
        static final int TRANSACTION_reqAvrcpStop = 33;
        static final int TRANSACTION_reqAvrcpStopFastForward = 40;
        static final int TRANSACTION_reqAvrcpStopRewind = 42;
        static final int TRANSACTION_reqAvrcpUnregisterEventWatcher = 51;
        static final int TRANSACTION_reqAvrcpUpdateSongStatus = 183;
        static final int TRANSACTION_reqAvrcpVolumeDown = 38;
        static final int TRANSACTION_reqAvrcpVolumeUp = 37;
        static final int TRANSACTION_reqBtConnectHfpA2dp = 83;
        static final int TRANSACTION_reqBtDisconnectAll = 84;
        static final int TRANSACTION_reqBtPair = 71;
        static final int TRANSACTION_reqBtPairedDevices = 73;
        static final int TRANSACTION_reqBtUnpair = 72;
        static final int TRANSACTION_reqGattServerAddCharacteristic = 190;
        static final int TRANSACTION_reqGattServerAddDescriptor = 191;
        static final int TRANSACTION_reqGattServerBeginServiceDeclaration = 189;
        static final int TRANSACTION_reqGattServerClearServices = 194;
        static final int TRANSACTION_reqGattServerDisconnect = 188;
        static final int TRANSACTION_reqGattServerEndServiceDeclaration = 192;
        static final int TRANSACTION_reqGattServerListen = 195;
        static final int TRANSACTION_reqGattServerRemoveService = 193;
        static final int TRANSACTION_reqGattServerSendNotification = 197;
        static final int TRANSACTION_reqGattServerSendResponse = 196;
        static final int TRANSACTION_reqHfpAnswerCall = 110;
        static final int TRANSACTION_reqHfpAudioTransferToCarkit = 114;
        static final int TRANSACTION_reqHfpAudioTransferToPhone = 115;
        static final int TRANSACTION_reqHfpConnect = 99;
        static final int TRANSACTION_reqHfpDialCall = 107;
        static final int TRANSACTION_reqHfpDisconnect = 100;
        static final int TRANSACTION_reqHfpMemoryDial = 109;
        static final int TRANSACTION_reqHfpReDial = 108;
        static final int TRANSACTION_reqHfpRejectIncomingCall = 111;
        static final int TRANSACTION_reqHfpSendDtmf = 113;
        static final int TRANSACTION_reqHfpTerminateCurrentCall = 112;
        static final int TRANSACTION_reqHfpVoiceDial = 118;
        static final int TRANSACTION_reqHidConnect = 154;
        static final int TRANSACTION_reqHidDisconnect = 155;
        static final int TRANSACTION_reqMapChangeReadStatus = 173;
        static final int TRANSACTION_reqMapCleanDatabase = 168;
        static final int TRANSACTION_reqMapDatabaseAvailable = 166;
        static final int TRANSACTION_reqMapDeleteDatabaseByAddress = 167;
        static final int TRANSACTION_reqMapDeleteMessage = 172;
        static final int TRANSACTION_reqMapDownloadInterrupt = 165;
        static final int TRANSACTION_reqMapDownloadMessage = 161;
        static final int TRANSACTION_reqMapDownloadSingleMessage = 160;
        static final int TRANSACTION_reqMapRegisterNotification = 162;
        static final int TRANSACTION_reqMapSendMessage = 171;
        static final int TRANSACTION_reqMapUnregisterNotification = 163;
        static final int TRANSACTION_reqOppAcceptReceiveFile = 179;
        static final int TRANSACTION_reqOppInterruptReceiveFile = 180;
        static final int TRANSACTION_reqPbapCleanDatabase = 139;
        static final int TRANSACTION_reqPbapDatabaseAvailable = 137;
        static final int TRANSACTION_reqPbapDatabaseQueryNameByNumber = 135;
        static final int TRANSACTION_reqPbapDatabaseQueryNameByPartialNumber = 136;
        static final int TRANSACTION_reqPbapDeleteDatabaseByAddress = 138;
        static final int TRANSACTION_reqPbapDownload = 129;
        static final int TRANSACTION_reqPbapDownloadInterrupt = 140;
        static final int TRANSACTION_reqPbapDownloadRange = 130;
        static final int TRANSACTION_reqPbapDownloadRangeToContactsProvider = 134;
        static final int TRANSACTION_reqPbapDownloadRangeToDatabase = 132;
        static final int TRANSACTION_reqPbapDownloadToContactsProvider = 133;
        static final int TRANSACTION_reqPbapDownloadToDatabase = 131;
        static final int TRANSACTION_reqSendHidMouseCommand = 156;
        static final int TRANSACTION_reqSendHidVirtualKeyCommand = 157;
        static final int TRANSACTION_reqSppConnect = 144;
        static final int TRANSACTION_reqSppConnectedDeviceAddressList = 146;
        static final int TRANSACTION_reqSppDisconnect = 145;
        static final int TRANSACTION_reqSppSendData = 148;
        static final int TRANSACTION_reqUsbBtPairedDevices = 215;
        static final int TRANSACTION_setA2dpLocalVolume = 20;
        static final int TRANSACTION_setA2dpStreamType = 21;
        static final int TRANSACTION_setBtAutoConnect = 88;
        static final int TRANSACTION_setBtDiscoverableTimeout = 68;
        static final int TRANSACTION_setBtEnable = 67;
        static final int TRANSACTION_setBtLocalName = 77;
        static final int TRANSACTION_setMapDownloadNotify = 174;
        static final int TRANSACTION_setOppFilePath = 177;
        static final int TRANSACTION_setPbapDownloadNotify = 141;
        static final int TRANSACTION_setTargetAddress = 181;
        static final int TRANSACTION_setUsbBtLocalName = 217;
        static final int TRANSACTION_setUsbEnabled = 208;
        static final int TRANSACTION_startA2dpRender = 19;
        static final int TRANSACTION_startBtDiscovery = 69;
        static final int TRANSACTION_startHfpRender = 120;
        static final int TRANSACTION_switchBtRoleMode = 86;
        static final int TRANSACTION_unregisterA2dpCallback = 12;
        static final int TRANSACTION_unregisterAvrcpCallback = 24;
        static final int TRANSACTION_unregisterBtCallback = 65;
        static final int TRANSACTION_unregisterGattServerCallback = 186;
        static final int TRANSACTION_unregisterHfpCallback = 94;
        static final int TRANSACTION_unregisterHidCallback = 150;
        static final int TRANSACTION_unregisterMapCallback = 159;
        static final int TRANSACTION_unregisterOppCallback = 176;
        static final int TRANSACTION_unregisterPbapCallback = 125;
        static final int TRANSACTION_unregisterSppCallback = 143;
        static final int TRANSACTION_unregisterUsbCallback = 202;
        static final int TRANSACTION_usbConnect = 205;
        static final int TRANSACTION_usbDisConnect = 206;
        static final int TRANSACTION_usbDisConnectDevice = 222;
        static final int TRANSACTION_usbReqUnpair = 207;
        static final int TRANSACTION_usbSearch = 203;
        static final int TRANSACTION_usbStopSearch = 204;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static UiCommand asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof UiCommand)) {
                return (UiCommand) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    parcel.enforceInterface(DESCRIPTOR);
                    String uiServiceVersionName = getUiServiceVersionName();
                    parcel2.writeNoException();
                    parcel2.writeString(uiServiceVersionName);
                    return true;
                case 2:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean isAvrcpServiceReady = isAvrcpServiceReady();
                    parcel2.writeNoException();
                    parcel2.writeInt(isAvrcpServiceReady ? 1 : 0);
                    return true;
                case 3:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean isA2dpServiceReady = isA2dpServiceReady();
                    parcel2.writeNoException();
                    parcel2.writeInt(isA2dpServiceReady ? 1 : 0);
                    return true;
                case 4:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean isSppServiceReady = isSppServiceReady();
                    parcel2.writeNoException();
                    parcel2.writeInt(isSppServiceReady ? 1 : 0);
                    return true;
                case 5:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean isBluetoothServiceReady = isBluetoothServiceReady();
                    parcel2.writeNoException();
                    parcel2.writeInt(isBluetoothServiceReady ? 1 : 0);
                    return true;
                case 6:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean isHfpServiceReady = isHfpServiceReady();
                    parcel2.writeNoException();
                    parcel2.writeInt(isHfpServiceReady ? 1 : 0);
                    return true;
                case 7:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean isHidServiceReady = isHidServiceReady();
                    parcel2.writeNoException();
                    parcel2.writeInt(isHidServiceReady ? 1 : 0);
                    return true;
                case 8:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean isPbapServiceReady = isPbapServiceReady();
                    parcel2.writeNoException();
                    parcel2.writeInt(isPbapServiceReady ? 1 : 0);
                    return true;
                case 9:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean isOppServiceReady = isOppServiceReady();
                    parcel2.writeNoException();
                    parcel2.writeInt(isOppServiceReady ? 1 : 0);
                    return true;
                case 10:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean isMapServiceReady = isMapServiceReady();
                    parcel2.writeNoException();
                    parcel2.writeInt(isMapServiceReady ? 1 : 0);
                    return true;
                case 11:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean registerA2dpCallback = registerA2dpCallback(UiCallbackA2dp.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    parcel2.writeInt(registerA2dpCallback ? 1 : 0);
                    return true;
                case 12:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean unregisterA2dpCallback = unregisterA2dpCallback(UiCallbackA2dp.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    parcel2.writeInt(unregisterA2dpCallback ? 1 : 0);
                    return true;
                case 13:
                    parcel.enforceInterface(DESCRIPTOR);
                    int a2dpConnectionState = getA2dpConnectionState();
                    parcel2.writeNoException();
                    parcel2.writeInt(a2dpConnectionState);
                    return true;
                case 14:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean isA2dpConnected = isA2dpConnected();
                    parcel2.writeNoException();
                    parcel2.writeInt(isA2dpConnected ? 1 : 0);
                    return true;
                case 15:
                    parcel.enforceInterface(DESCRIPTOR);
                    String a2dpConnectedAddress = getA2dpConnectedAddress();
                    parcel2.writeNoException();
                    parcel2.writeString(a2dpConnectedAddress);
                    return true;
                case 16:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqA2dpConnect = reqA2dpConnect(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(reqA2dpConnect ? 1 : 0);
                    return true;
                case 17:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqA2dpDisconnect = reqA2dpDisconnect(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(reqA2dpDisconnect ? 1 : 0);
                    return true;
                case 18:
                    parcel.enforceInterface(DESCRIPTOR);
                    pauseA2dpRender();
                    parcel2.writeNoException();
                    return true;
                case 19:
                    parcel.enforceInterface(DESCRIPTOR);
                    startA2dpRender();
                    parcel2.writeNoException();
                    return true;
                case 20:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean a2dpLocalVolume = setA2dpLocalVolume(parcel.readFloat());
                    parcel2.writeNoException();
                    parcel2.writeInt(a2dpLocalVolume ? 1 : 0);
                    return true;
                case 21:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean a2dpStreamType = setA2dpStreamType(parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeInt(a2dpStreamType ? 1 : 0);
                    return true;
                case 22:
                    parcel.enforceInterface(DESCRIPTOR);
                    int a2dpStreamType2 = getA2dpStreamType();
                    parcel2.writeNoException();
                    parcel2.writeInt(a2dpStreamType2);
                    return true;
                case 23:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean registerAvrcpCallback = registerAvrcpCallback(UiCallbackAvrcp.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    parcel2.writeInt(registerAvrcpCallback ? 1 : 0);
                    return true;
                case 24:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean unregisterAvrcpCallback = unregisterAvrcpCallback(UiCallbackAvrcp.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    parcel2.writeInt(unregisterAvrcpCallback ? 1 : 0);
                    return true;
                case 25:
                    parcel.enforceInterface(DESCRIPTOR);
                    int avrcpConnectionState = getAvrcpConnectionState();
                    parcel2.writeNoException();
                    parcel2.writeInt(avrcpConnectionState);
                    return true;
                case 26:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean isAvrcpConnected = isAvrcpConnected();
                    parcel2.writeNoException();
                    parcel2.writeInt(isAvrcpConnected ? 1 : 0);
                    return true;
                case 27:
                    parcel.enforceInterface(DESCRIPTOR);
                    String avrcpConnectedAddress = getAvrcpConnectedAddress();
                    parcel2.writeNoException();
                    parcel2.writeString(avrcpConnectedAddress);
                    return true;
                case 28:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqAvrcpConnect = reqAvrcpConnect(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(reqAvrcpConnect ? 1 : 0);
                    return true;
                case 29:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqAvrcpDisconnect = reqAvrcpDisconnect(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(reqAvrcpDisconnect ? 1 : 0);
                    return true;
                case 30:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean isAvrcp13Supported = isAvrcp13Supported(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(isAvrcp13Supported ? 1 : 0);
                    return true;
                case 31:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean isAvrcp14Supported = isAvrcp14Supported(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(isAvrcp14Supported ? 1 : 0);
                    return true;
                case 32:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqAvrcpPlay = reqAvrcpPlay();
                    parcel2.writeNoException();
                    parcel2.writeInt(reqAvrcpPlay ? 1 : 0);
                    return true;
                case 33:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqAvrcpStop = reqAvrcpStop();
                    parcel2.writeNoException();
                    parcel2.writeInt(reqAvrcpStop ? 1 : 0);
                    return true;
                case 34:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqAvrcpPause = reqAvrcpPause();
                    parcel2.writeNoException();
                    parcel2.writeInt(reqAvrcpPause ? 1 : 0);
                    return true;
                case 35:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqAvrcpForward = reqAvrcpForward();
                    parcel2.writeNoException();
                    parcel2.writeInt(reqAvrcpForward ? 1 : 0);
                    return true;
                case 36:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqAvrcpBackward = reqAvrcpBackward();
                    parcel2.writeNoException();
                    parcel2.writeInt(reqAvrcpBackward ? 1 : 0);
                    return true;
                case 37:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqAvrcpVolumeUp = reqAvrcpVolumeUp();
                    parcel2.writeNoException();
                    parcel2.writeInt(reqAvrcpVolumeUp ? 1 : 0);
                    return true;
                case 38:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqAvrcpVolumeDown = reqAvrcpVolumeDown();
                    parcel2.writeNoException();
                    parcel2.writeInt(reqAvrcpVolumeDown ? 1 : 0);
                    return true;
                case 39:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqAvrcpStartFastForward = reqAvrcpStartFastForward();
                    parcel2.writeNoException();
                    parcel2.writeInt(reqAvrcpStartFastForward ? 1 : 0);
                    return true;
                case 40:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqAvrcpStopFastForward = reqAvrcpStopFastForward();
                    parcel2.writeNoException();
                    parcel2.writeInt(reqAvrcpStopFastForward ? 1 : 0);
                    return true;
                case 41:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqAvrcpStartRewind = reqAvrcpStartRewind();
                    parcel2.writeNoException();
                    parcel2.writeInt(reqAvrcpStartRewind ? 1 : 0);
                    return true;
                case 42:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqAvrcpStopRewind = reqAvrcpStopRewind();
                    parcel2.writeNoException();
                    parcel2.writeInt(reqAvrcpStopRewind ? 1 : 0);
                    return true;
                case 43:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqAvrcp13GetCapabilitiesSupportEvent = reqAvrcp13GetCapabilitiesSupportEvent();
                    parcel2.writeNoException();
                    parcel2.writeInt(reqAvrcp13GetCapabilitiesSupportEvent ? 1 : 0);
                    return true;
                case 44:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqAvrcp13GetPlayerSettingAttributesList = reqAvrcp13GetPlayerSettingAttributesList();
                    parcel2.writeNoException();
                    parcel2.writeInt(reqAvrcp13GetPlayerSettingAttributesList ? 1 : 0);
                    return true;
                case 45:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqAvrcp13GetPlayerSettingValuesList = reqAvrcp13GetPlayerSettingValuesList(parcel.readByte());
                    parcel2.writeNoException();
                    parcel2.writeInt(reqAvrcp13GetPlayerSettingValuesList ? 1 : 0);
                    return true;
                case 46:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqAvrcp13GetPlayerSettingCurrentValues = reqAvrcp13GetPlayerSettingCurrentValues();
                    parcel2.writeNoException();
                    parcel2.writeInt(reqAvrcp13GetPlayerSettingCurrentValues ? 1 : 0);
                    return true;
                case 47:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqAvrcp13SetPlayerSettingValue = reqAvrcp13SetPlayerSettingValue(parcel.readByte(), parcel.readByte());
                    parcel2.writeNoException();
                    parcel2.writeInt(reqAvrcp13SetPlayerSettingValue ? 1 : 0);
                    return true;
                case 48:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqAvrcp13GetElementAttributesPlaying = reqAvrcp13GetElementAttributesPlaying();
                    parcel2.writeNoException();
                    parcel2.writeInt(reqAvrcp13GetElementAttributesPlaying ? 1 : 0);
                    return true;
                case 49:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqAvrcp13GetPlayStatus = reqAvrcp13GetPlayStatus();
                    parcel2.writeNoException();
                    parcel2.writeInt(reqAvrcp13GetPlayStatus ? 1 : 0);
                    return true;
                case 50:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqAvrcpRegisterEventWatcher = reqAvrcpRegisterEventWatcher(parcel.readByte(), parcel.readLong());
                    parcel2.writeNoException();
                    parcel2.writeInt(reqAvrcpRegisterEventWatcher ? 1 : 0);
                    return true;
                case 51:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqAvrcpUnregisterEventWatcher = reqAvrcpUnregisterEventWatcher(parcel.readByte());
                    parcel2.writeNoException();
                    parcel2.writeInt(reqAvrcpUnregisterEventWatcher ? 1 : 0);
                    return true;
                case 52:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqAvrcp13NextGroup = reqAvrcp13NextGroup();
                    parcel2.writeNoException();
                    parcel2.writeInt(reqAvrcp13NextGroup ? 1 : 0);
                    return true;
                case 53:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqAvrcp13PreviousGroup = reqAvrcp13PreviousGroup();
                    parcel2.writeNoException();
                    parcel2.writeInt(reqAvrcp13PreviousGroup ? 1 : 0);
                    return true;
                case 54:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean isAvrcp14BrowsingChannelEstablished = isAvrcp14BrowsingChannelEstablished();
                    parcel2.writeNoException();
                    parcel2.writeInt(isAvrcp14BrowsingChannelEstablished ? 1 : 0);
                    return true;
                case 55:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqAvrcp14SetAddressedPlayer = reqAvrcp14SetAddressedPlayer(parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeInt(reqAvrcp14SetAddressedPlayer ? 1 : 0);
                    return true;
                case 56:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqAvrcp14SetBrowsedPlayer = reqAvrcp14SetBrowsedPlayer(parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeInt(reqAvrcp14SetBrowsedPlayer ? 1 : 0);
                    return true;
                case 57:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqAvrcp14GetFolderItems = reqAvrcp14GetFolderItems(parcel.readByte());
                    parcel2.writeNoException();
                    parcel2.writeInt(reqAvrcp14GetFolderItems ? 1 : 0);
                    return true;
                case 58:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqAvrcp14ChangePath = reqAvrcp14ChangePath(parcel.readInt(), parcel.readLong(), parcel.readByte());
                    parcel2.writeNoException();
                    parcel2.writeInt(reqAvrcp14ChangePath ? 1 : 0);
                    return true;
                case 59:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqAvrcp14GetItemAttributes = reqAvrcp14GetItemAttributes(parcel.readByte(), parcel.readInt(), parcel.readLong());
                    parcel2.writeNoException();
                    parcel2.writeInt(reqAvrcp14GetItemAttributes ? 1 : 0);
                    return true;
                case 60:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqAvrcp14PlaySelectedItem = reqAvrcp14PlaySelectedItem(parcel.readByte(), parcel.readInt(), parcel.readLong());
                    parcel2.writeNoException();
                    parcel2.writeInt(reqAvrcp14PlaySelectedItem ? 1 : 0);
                    return true;
                case 61:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqAvrcp14Search = reqAvrcp14Search(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(reqAvrcp14Search ? 1 : 0);
                    return true;
                case 62:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqAvrcp14AddToNowPlaying = reqAvrcp14AddToNowPlaying(parcel.readByte(), parcel.readInt(), parcel.readLong());
                    parcel2.writeNoException();
                    parcel2.writeInt(reqAvrcp14AddToNowPlaying ? 1 : 0);
                    return true;
                case 63:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqAvrcp14SetAbsoluteVolume = reqAvrcp14SetAbsoluteVolume(parcel.readByte());
                    parcel2.writeNoException();
                    parcel2.writeInt(reqAvrcp14SetAbsoluteVolume ? 1 : 0);
                    return true;
                case 64:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean registerBtCallback = registerBtCallback(UiCallbackBluetooth.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    parcel2.writeInt(registerBtCallback ? 1 : 0);
                    return true;
                case 65:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean unregisterBtCallback = unregisterBtCallback(UiCallbackBluetooth.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    parcel2.writeInt(unregisterBtCallback ? 1 : 0);
                    return true;
                case 66:
                    parcel.enforceInterface(DESCRIPTOR);
                    String nfServiceVersionName = getNfServiceVersionName();
                    parcel2.writeNoException();
                    parcel2.writeString(nfServiceVersionName);
                    return true;
                case 67:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean btEnable = setBtEnable(parcel.readInt() != 0);
                    parcel2.writeNoException();
                    parcel2.writeInt(btEnable ? 1 : 0);
                    return true;
                case 68:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean btDiscoverableTimeout = setBtDiscoverableTimeout(parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeInt(btDiscoverableTimeout ? 1 : 0);
                    return true;
                case 69:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean startBtDiscovery = startBtDiscovery();
                    parcel2.writeNoException();
                    parcel2.writeInt(startBtDiscovery ? 1 : 0);
                    return true;
                case 70:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean cancelBtDiscovery = cancelBtDiscovery();
                    parcel2.writeNoException();
                    parcel2.writeInt(cancelBtDiscovery ? 1 : 0);
                    return true;
                case 71:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqBtPair = reqBtPair(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(reqBtPair ? 1 : 0);
                    return true;
                case 72:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqBtUnpair = reqBtUnpair(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(reqBtUnpair ? 1 : 0);
                    return true;
                case 73:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqBtPairedDevices = reqBtPairedDevices();
                    parcel2.writeNoException();
                    parcel2.writeInt(reqBtPairedDevices ? 1 : 0);
                    return true;
                case 74:
                    parcel.enforceInterface(DESCRIPTOR);
                    String btLocalName = getBtLocalName();
                    parcel2.writeNoException();
                    parcel2.writeString(btLocalName);
                    return true;
                case 75:
                    parcel.enforceInterface(DESCRIPTOR);
                    String btRemoteDeviceName = getBtRemoteDeviceName(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeString(btRemoteDeviceName);
                    return true;
                case 76:
                    parcel.enforceInterface(DESCRIPTOR);
                    String btLocalAddress = getBtLocalAddress();
                    parcel2.writeNoException();
                    parcel2.writeString(btLocalAddress);
                    return true;
                case 77:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean btLocalName2 = setBtLocalName(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(btLocalName2 ? 1 : 0);
                    return true;
                case 78:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean isBtEnabled = isBtEnabled();
                    parcel2.writeNoException();
                    parcel2.writeInt(isBtEnabled ? 1 : 0);
                    return true;
                case 79:
                    parcel.enforceInterface(DESCRIPTOR);
                    int btState = getBtState();
                    parcel2.writeNoException();
                    parcel2.writeInt(btState);
                    return true;
                case 80:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean isBtDiscovering = isBtDiscovering();
                    parcel2.writeNoException();
                    parcel2.writeInt(isBtDiscovering ? 1 : 0);
                    return true;
                case 81:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean isBtDiscoverable = isBtDiscoverable();
                    parcel2.writeNoException();
                    parcel2.writeInt(isBtDiscoverable ? 1 : 0);
                    return true;
                case 82:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean isBtAutoConnectEnable = isBtAutoConnectEnable();
                    parcel2.writeNoException();
                    parcel2.writeInt(isBtAutoConnectEnable ? 1 : 0);
                    return true;
                case 83:
                    parcel.enforceInterface(DESCRIPTOR);
                    int reqBtConnectHfpA2dp = reqBtConnectHfpA2dp(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(reqBtConnectHfpA2dp);
                    return true;
                case 84:
                    parcel.enforceInterface(DESCRIPTOR);
                    int reqBtDisconnectAll = reqBtDisconnectAll();
                    parcel2.writeNoException();
                    parcel2.writeInt(reqBtDisconnectAll);
                    return true;
                case 85:
                    parcel.enforceInterface(DESCRIPTOR);
                    int btRemoteUuids = getBtRemoteUuids(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(btRemoteUuids);
                    return true;
                case 86:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean switchBtRoleMode = switchBtRoleMode(parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeInt(switchBtRoleMode ? 1 : 0);
                    return true;
                case 87:
                    parcel.enforceInterface(DESCRIPTOR);
                    int btRoleMode = getBtRoleMode();
                    parcel2.writeNoException();
                    parcel2.writeInt(btRoleMode);
                    return true;
                case 88:
                    parcel.enforceInterface(DESCRIPTOR);
                    setBtAutoConnect(parcel.readInt(), parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 89:
                    parcel.enforceInterface(DESCRIPTOR);
                    int btAutoConnectCondition = getBtAutoConnectCondition();
                    parcel2.writeNoException();
                    parcel2.writeInt(btAutoConnectCondition);
                    return true;
                case 90:
                    parcel.enforceInterface(DESCRIPTOR);
                    int btAutoConnectPeriod = getBtAutoConnectPeriod();
                    parcel2.writeNoException();
                    parcel2.writeInt(btAutoConnectPeriod);
                    return true;
                case 91:
                    parcel.enforceInterface(DESCRIPTOR);
                    int btAutoConnectState = getBtAutoConnectState();
                    parcel2.writeNoException();
                    parcel2.writeInt(btAutoConnectState);
                    return true;
                case 92:
                    parcel.enforceInterface(DESCRIPTOR);
                    String btAutoConnectingAddress = getBtAutoConnectingAddress();
                    parcel2.writeNoException();
                    parcel2.writeString(btAutoConnectingAddress);
                    return true;
                case 93:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean registerHfpCallback = registerHfpCallback(UiCallbackHfp.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    parcel2.writeInt(registerHfpCallback ? 1 : 0);
                    return true;
                case 94:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean unregisterHfpCallback = unregisterHfpCallback(UiCallbackHfp.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    parcel2.writeInt(unregisterHfpCallback ? 1 : 0);
                    return true;
                case 95:
                    parcel.enforceInterface(DESCRIPTOR);
                    int hfpConnectionState = getHfpConnectionState();
                    parcel2.writeNoException();
                    parcel2.writeInt(hfpConnectionState);
                    return true;
                case 96:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean isHfpConnected = isHfpConnected();
                    parcel2.writeNoException();
                    parcel2.writeInt(isHfpConnected ? 1 : 0);
                    return true;
                case 97:
                    parcel.enforceInterface(DESCRIPTOR);
                    String hfpConnectedAddress = getHfpConnectedAddress();
                    parcel2.writeNoException();
                    parcel2.writeString(hfpConnectedAddress);
                    return true;
                case 98:
                    parcel.enforceInterface(DESCRIPTOR);
                    int hfpAudioConnectionState = getHfpAudioConnectionState();
                    parcel2.writeNoException();
                    parcel2.writeInt(hfpAudioConnectionState);
                    return true;
                case 99:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqHfpConnect = reqHfpConnect(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(reqHfpConnect ? 1 : 0);
                    return true;
                case 100:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqHfpDisconnect = reqHfpDisconnect(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(reqHfpDisconnect ? 1 : 0);
                    return true;
                case 101:
                    parcel.enforceInterface(DESCRIPTOR);
                    int hfpRemoteSignalStrength = getHfpRemoteSignalStrength();
                    parcel2.writeNoException();
                    parcel2.writeInt(hfpRemoteSignalStrength);
                    return true;
                case 102:
                    parcel.enforceInterface(DESCRIPTOR);
                    List<NfHfpClientCall> hfpCallList = getHfpCallList();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(hfpCallList);
                    return true;
                case 103:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean isHfpRemoteOnRoaming = isHfpRemoteOnRoaming();
                    parcel2.writeNoException();
                    parcel2.writeInt(isHfpRemoteOnRoaming ? 1 : 0);
                    return true;
                case 104:
                    parcel.enforceInterface(DESCRIPTOR);
                    int hfpRemoteBatteryIndicator = getHfpRemoteBatteryIndicator();
                    parcel2.writeNoException();
                    parcel2.writeInt(hfpRemoteBatteryIndicator);
                    return true;
                case 105:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean isHfpRemoteTelecomServiceOn = isHfpRemoteTelecomServiceOn();
                    parcel2.writeNoException();
                    parcel2.writeInt(isHfpRemoteTelecomServiceOn ? 1 : 0);
                    return true;
                case 106:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean isHfpRemoteVoiceDialOn = isHfpRemoteVoiceDialOn();
                    parcel2.writeNoException();
                    parcel2.writeInt(isHfpRemoteVoiceDialOn ? 1 : 0);
                    return true;
                case 107:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqHfpDialCall = reqHfpDialCall(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(reqHfpDialCall ? 1 : 0);
                    return true;
                case 108:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqHfpReDial = reqHfpReDial();
                    parcel2.writeNoException();
                    parcel2.writeInt(reqHfpReDial ? 1 : 0);
                    return true;
                case 109:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqHfpMemoryDial = reqHfpMemoryDial(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(reqHfpMemoryDial ? 1 : 0);
                    return true;
                case 110:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqHfpAnswerCall = reqHfpAnswerCall(parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeInt(reqHfpAnswerCall ? 1 : 0);
                    return true;
                case 111:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqHfpRejectIncomingCall = reqHfpRejectIncomingCall();
                    parcel2.writeNoException();
                    parcel2.writeInt(reqHfpRejectIncomingCall ? 1 : 0);
                    return true;
                case 112:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqHfpTerminateCurrentCall = reqHfpTerminateCurrentCall();
                    parcel2.writeNoException();
                    parcel2.writeInt(reqHfpTerminateCurrentCall ? 1 : 0);
                    return true;
                case 113:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqHfpSendDtmf = reqHfpSendDtmf(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(reqHfpSendDtmf ? 1 : 0);
                    return true;
                case 114:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqHfpAudioTransferToCarkit = reqHfpAudioTransferToCarkit();
                    parcel2.writeNoException();
                    parcel2.writeInt(reqHfpAudioTransferToCarkit ? 1 : 0);
                    return true;
                case 115:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqHfpAudioTransferToPhone = reqHfpAudioTransferToPhone();
                    parcel2.writeNoException();
                    parcel2.writeInt(reqHfpAudioTransferToPhone ? 1 : 0);
                    return true;
                case 116:
                    parcel.enforceInterface(DESCRIPTOR);
                    String hfpRemoteNetworkOperator = getHfpRemoteNetworkOperator();
                    parcel2.writeNoException();
                    parcel2.writeString(hfpRemoteNetworkOperator);
                    return true;
                case 117:
                    parcel.enforceInterface(DESCRIPTOR);
                    String hfpRemoteSubscriberNumber = getHfpRemoteSubscriberNumber();
                    parcel2.writeNoException();
                    parcel2.writeString(hfpRemoteSubscriberNumber);
                    return true;
                case 118:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqHfpVoiceDial = reqHfpVoiceDial(parcel.readInt() != 0);
                    parcel2.writeNoException();
                    parcel2.writeInt(reqHfpVoiceDial ? 1 : 0);
                    return true;
                case 119:
                    parcel.enforceInterface(DESCRIPTOR);
                    pauseHfpRender();
                    parcel2.writeNoException();
                    return true;
                case 120:
                    parcel.enforceInterface(DESCRIPTOR);
                    startHfpRender();
                    parcel2.writeNoException();
                    return true;
                case 121:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean isHfpMicMute = isHfpMicMute();
                    parcel2.writeNoException();
                    parcel2.writeInt(isHfpMicMute ? 1 : 0);
                    return true;
                case 122:
                    parcel.enforceInterface(DESCRIPTOR);
                    muteHfpMic(parcel.readInt() != 0);
                    parcel2.writeNoException();
                    return true;
                case 123:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean isHfpInBandRingtoneSupport = isHfpInBandRingtoneSupport();
                    parcel2.writeNoException();
                    parcel2.writeInt(isHfpInBandRingtoneSupport ? 1 : 0);
                    return true;
                case 124:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean registerPbapCallback = registerPbapCallback(UiCallbackPbap.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    parcel2.writeInt(registerPbapCallback ? 1 : 0);
                    return true;
                case 125:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean unregisterPbapCallback = unregisterPbapCallback(UiCallbackPbap.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    parcel2.writeInt(unregisterPbapCallback ? 1 : 0);
                    return true;
                case 126:
                    parcel.enforceInterface(DESCRIPTOR);
                    int pbapConnectionState = getPbapConnectionState();
                    parcel2.writeNoException();
                    parcel2.writeInt(pbapConnectionState);
                    return true;
                case TRANSACTION_isPbapDownloading /* 127 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean isPbapDownloading = isPbapDownloading();
                    parcel2.writeNoException();
                    parcel2.writeInt(isPbapDownloading ? 1 : 0);
                    return true;
                case 128:
                    parcel.enforceInterface(DESCRIPTOR);
                    String pbapDownloadingAddress = getPbapDownloadingAddress();
                    parcel2.writeNoException();
                    parcel2.writeString(pbapDownloadingAddress);
                    return true;
                case 129:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqPbapDownload = reqPbapDownload(parcel.readString(), parcel.readInt(), parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeInt(reqPbapDownload ? 1 : 0);
                    return true;
                case 130:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqPbapDownloadRange = reqPbapDownloadRange(parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeInt(reqPbapDownloadRange ? 1 : 0);
                    return true;
                case 131:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqPbapDownloadToDatabase = reqPbapDownloadToDatabase(parcel.readString(), parcel.readInt(), parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeInt(reqPbapDownloadToDatabase ? 1 : 0);
                    return true;
                case 132:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqPbapDownloadRangeToDatabase = reqPbapDownloadRangeToDatabase(parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeInt(reqPbapDownloadRangeToDatabase ? 1 : 0);
                    return true;
                case 133:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqPbapDownloadToContactsProvider = reqPbapDownloadToContactsProvider(parcel.readString(), parcel.readInt(), parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeInt(reqPbapDownloadToContactsProvider ? 1 : 0);
                    return true;
                case 134:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqPbapDownloadRangeToContactsProvider = reqPbapDownloadRangeToContactsProvider(parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeInt(reqPbapDownloadRangeToContactsProvider ? 1 : 0);
                    return true;
                case 135:
                    parcel.enforceInterface(DESCRIPTOR);
                    reqPbapDatabaseQueryNameByNumber(parcel.readString(), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 136:
                    parcel.enforceInterface(DESCRIPTOR);
                    reqPbapDatabaseQueryNameByPartialNumber(parcel.readString(), parcel.readString(), parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 137:
                    parcel.enforceInterface(DESCRIPTOR);
                    reqPbapDatabaseAvailable(parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 138:
                    parcel.enforceInterface(DESCRIPTOR);
                    reqPbapDeleteDatabaseByAddress(parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 139:
                    parcel.enforceInterface(DESCRIPTOR);
                    reqPbapCleanDatabase();
                    parcel2.writeNoException();
                    return true;
                case 140:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqPbapDownloadInterrupt = reqPbapDownloadInterrupt(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(reqPbapDownloadInterrupt ? 1 : 0);
                    return true;
                case 141:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean pbapDownloadNotify = setPbapDownloadNotify(parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeInt(pbapDownloadNotify ? 1 : 0);
                    return true;
                case 142:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean registerSppCallback = registerSppCallback(UiCallbackSpp.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    parcel2.writeInt(registerSppCallback ? 1 : 0);
                    return true;
                case TRANSACTION_unregisterSppCallback /* 143 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean unregisterSppCallback = unregisterSppCallback(UiCallbackSpp.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    parcel2.writeInt(unregisterSppCallback ? 1 : 0);
                    return true;
                case TRANSACTION_reqSppConnect /* 144 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqSppConnect = reqSppConnect(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(reqSppConnect ? 1 : 0);
                    return true;
                case 145:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqSppDisconnect = reqSppDisconnect(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(reqSppDisconnect ? 1 : 0);
                    return true;
                case TRANSACTION_reqSppConnectedDeviceAddressList /* 146 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    reqSppConnectedDeviceAddressList();
                    parcel2.writeNoException();
                    return true;
                case TRANSACTION_isSppConnected /* 147 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean isSppConnected = isSppConnected(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(isSppConnected ? 1 : 0);
                    return true;
                case 148:
                    parcel.enforceInterface(DESCRIPTOR);
                    reqSppSendData(parcel.readString(), parcel.createByteArray());
                    parcel2.writeNoException();
                    return true;
                case TRANSACTION_registerHidCallback /* 149 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean registerHidCallback = registerHidCallback(UiCallbackHid.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    parcel2.writeInt(registerHidCallback ? 1 : 0);
                    return true;
                case 150:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean unregisterHidCallback = unregisterHidCallback(UiCallbackHid.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    parcel2.writeInt(unregisterHidCallback ? 1 : 0);
                    return true;
                case TRANSACTION_getHidConnectionState /* 151 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    int hidConnectionState = getHidConnectionState();
                    parcel2.writeNoException();
                    parcel2.writeInt(hidConnectionState);
                    return true;
                case TRANSACTION_isHidConnected /* 152 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean isHidConnected = isHidConnected();
                    parcel2.writeNoException();
                    parcel2.writeInt(isHidConnected ? 1 : 0);
                    return true;
                case TRANSACTION_getHidConnectedAddress /* 153 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    String hidConnectedAddress = getHidConnectedAddress();
                    parcel2.writeNoException();
                    parcel2.writeString(hidConnectedAddress);
                    return true;
                case TRANSACTION_reqHidConnect /* 154 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqHidConnect = reqHidConnect(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(reqHidConnect ? 1 : 0);
                    return true;
                case 155:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqHidDisconnect = reqHidDisconnect(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(reqHidDisconnect ? 1 : 0);
                    return true;
                case TRANSACTION_reqSendHidMouseCommand /* 156 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqSendHidMouseCommand = reqSendHidMouseCommand(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeInt(reqSendHidMouseCommand ? 1 : 0);
                    return true;
                case TRANSACTION_reqSendHidVirtualKeyCommand /* 157 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqSendHidVirtualKeyCommand = reqSendHidVirtualKeyCommand(parcel.readInt(), parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeInt(reqSendHidVirtualKeyCommand ? 1 : 0);
                    return true;
                case TRANSACTION_registerMapCallback /* 158 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean registerMapCallback = registerMapCallback(UiCallbackMap.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    parcel2.writeInt(registerMapCallback ? 1 : 0);
                    return true;
                case TRANSACTION_unregisterMapCallback /* 159 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean unregisterMapCallback = unregisterMapCallback(UiCallbackMap.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    parcel2.writeInt(unregisterMapCallback ? 1 : 0);
                    return true;
                case 160:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqMapDownloadSingleMessage = reqMapDownloadSingleMessage(parcel.readString(), parcel.readInt(), parcel.readString(), parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeInt(reqMapDownloadSingleMessage ? 1 : 0);
                    return true;
                case TRANSACTION_reqMapDownloadMessage /* 161 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqMapDownloadMessage = reqMapDownloadMessage(parcel.readString(), parcel.readInt(), parcel.readInt() != 0, parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readInt(), parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeInt(reqMapDownloadMessage ? 1 : 0);
                    return true;
                case TRANSACTION_reqMapRegisterNotification /* 162 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqMapRegisterNotification = reqMapRegisterNotification(parcel.readString(), parcel.readInt() != 0);
                    parcel2.writeNoException();
                    parcel2.writeInt(reqMapRegisterNotification ? 1 : 0);
                    return true;
                case TRANSACTION_reqMapUnregisterNotification /* 163 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    reqMapUnregisterNotification(parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case TRANSACTION_isMapNotificationRegistered /* 164 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean isMapNotificationRegistered = isMapNotificationRegistered(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(isMapNotificationRegistered ? 1 : 0);
                    return true;
                case TRANSACTION_reqMapDownloadInterrupt /* 165 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqMapDownloadInterrupt = reqMapDownloadInterrupt(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(reqMapDownloadInterrupt ? 1 : 0);
                    return true;
                case TRANSACTION_reqMapDatabaseAvailable /* 166 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    reqMapDatabaseAvailable();
                    parcel2.writeNoException();
                    return true;
                case TRANSACTION_reqMapDeleteDatabaseByAddress /* 167 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    reqMapDeleteDatabaseByAddress(parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case TRANSACTION_reqMapCleanDatabase /* 168 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    reqMapCleanDatabase();
                    parcel2.writeNoException();
                    return true;
                case TRANSACTION_getMapCurrentState /* 169 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    int mapCurrentState = getMapCurrentState(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(mapCurrentState);
                    return true;
                case 170:
                    parcel.enforceInterface(DESCRIPTOR);
                    int mapRegisterState = getMapRegisterState(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(mapRegisterState);
                    return true;
                case TRANSACTION_reqMapSendMessage /* 171 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqMapSendMessage = reqMapSendMessage(parcel.readString(), parcel.readString(), parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(reqMapSendMessage ? 1 : 0);
                    return true;
                case TRANSACTION_reqMapDeleteMessage /* 172 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqMapDeleteMessage = reqMapDeleteMessage(parcel.readString(), parcel.readInt(), parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(reqMapDeleteMessage ? 1 : 0);
                    return true;
                case TRANSACTION_reqMapChangeReadStatus /* 173 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqMapChangeReadStatus = reqMapChangeReadStatus(parcel.readString(), parcel.readInt(), parcel.readString(), parcel.readInt() != 0);
                    parcel2.writeNoException();
                    parcel2.writeInt(reqMapChangeReadStatus ? 1 : 0);
                    return true;
                case TRANSACTION_setMapDownloadNotify /* 174 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean mapDownloadNotify = setMapDownloadNotify(parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeInt(mapDownloadNotify ? 1 : 0);
                    return true;
                case TRANSACTION_registerOppCallback /* 175 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean registerOppCallback = registerOppCallback(UiCallbackOpp.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    parcel2.writeInt(registerOppCallback ? 1 : 0);
                    return true;
                case TRANSACTION_unregisterOppCallback /* 176 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean unregisterOppCallback = unregisterOppCallback(UiCallbackOpp.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    parcel2.writeInt(unregisterOppCallback ? 1 : 0);
                    return true;
                case TRANSACTION_setOppFilePath /* 177 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean oppFilePath = setOppFilePath(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(oppFilePath ? 1 : 0);
                    return true;
                case TRANSACTION_getOppFilePath /* 178 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    String oppFilePath2 = getOppFilePath();
                    parcel2.writeNoException();
                    parcel2.writeString(oppFilePath2);
                    return true;
                case TRANSACTION_reqOppAcceptReceiveFile /* 179 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqOppAcceptReceiveFile = reqOppAcceptReceiveFile(parcel.readInt() != 0);
                    parcel2.writeNoException();
                    parcel2.writeInt(reqOppAcceptReceiveFile ? 1 : 0);
                    return true;
                case TRANSACTION_reqOppInterruptReceiveFile /* 180 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqOppInterruptReceiveFile = reqOppInterruptReceiveFile();
                    parcel2.writeNoException();
                    parcel2.writeInt(reqOppInterruptReceiveFile ? 1 : 0);
                    return true;
                case TRANSACTION_setTargetAddress /* 181 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    setTargetAddress(parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case TRANSACTION_getTargetAddress /* 182 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    String targetAddress = getTargetAddress();
                    parcel2.writeNoException();
                    parcel2.writeString(targetAddress);
                    return true;
                case TRANSACTION_reqAvrcpUpdateSongStatus /* 183 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    reqAvrcpUpdateSongStatus();
                    parcel2.writeNoException();
                    return true;
                case TRANSACTION_isGattServiceReady /* 184 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean isGattServiceReady = isGattServiceReady();
                    parcel2.writeNoException();
                    parcel2.writeInt(isGattServiceReady ? 1 : 0);
                    return true;
                case TRANSACTION_registerGattServerCallback /* 185 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean registerGattServerCallback = registerGattServerCallback(UiCallbackGattServer.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    parcel2.writeInt(registerGattServerCallback ? 1 : 0);
                    return true;
                case TRANSACTION_unregisterGattServerCallback /* 186 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean unregisterGattServerCallback = unregisterGattServerCallback(UiCallbackGattServer.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    parcel2.writeInt(unregisterGattServerCallback ? 1 : 0);
                    return true;
                case TRANSACTION_getGattServerConnectionState /* 187 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    int gattServerConnectionState = getGattServerConnectionState();
                    parcel2.writeNoException();
                    parcel2.writeInt(gattServerConnectionState);
                    return true;
                case TRANSACTION_reqGattServerDisconnect /* 188 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqGattServerDisconnect = reqGattServerDisconnect(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(reqGattServerDisconnect ? 1 : 0);
                    return true;
                case TRANSACTION_reqGattServerBeginServiceDeclaration /* 189 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqGattServerBeginServiceDeclaration = reqGattServerBeginServiceDeclaration(parcel.readInt(), parcel.readInt() != 0 ? (ParcelUuid) ParcelUuid.CREATOR.createFromParcel(parcel) : null);
                    parcel2.writeNoException();
                    parcel2.writeInt(reqGattServerBeginServiceDeclaration ? 1 : 0);
                    return true;
                case TRANSACTION_reqGattServerAddCharacteristic /* 190 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqGattServerAddCharacteristic = reqGattServerAddCharacteristic(parcel.readInt() != 0 ? (ParcelUuid) ParcelUuid.CREATOR.createFromParcel(parcel) : null, parcel.readInt(), parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeInt(reqGattServerAddCharacteristic ? 1 : 0);
                    return true;
                case TRANSACTION_reqGattServerAddDescriptor /* 191 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqGattServerAddDescriptor = reqGattServerAddDescriptor(parcel.readInt() != 0 ? (ParcelUuid) ParcelUuid.CREATOR.createFromParcel(parcel) : null, parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeInt(reqGattServerAddDescriptor ? 1 : 0);
                    return true;
                case 192:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqGattServerEndServiceDeclaration = reqGattServerEndServiceDeclaration();
                    parcel2.writeNoException();
                    parcel2.writeInt(reqGattServerEndServiceDeclaration ? 1 : 0);
                    return true;
                case TRANSACTION_reqGattServerRemoveService /* 193 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqGattServerRemoveService = reqGattServerRemoveService(parcel.readInt(), parcel.readInt() != 0 ? (ParcelUuid) ParcelUuid.CREATOR.createFromParcel(parcel) : null);
                    parcel2.writeNoException();
                    parcel2.writeInt(reqGattServerRemoveService ? 1 : 0);
                    return true;
                case TRANSACTION_reqGattServerClearServices /* 194 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqGattServerClearServices = reqGattServerClearServices();
                    parcel2.writeNoException();
                    parcel2.writeInt(reqGattServerClearServices ? 1 : 0);
                    return true;
                case TRANSACTION_reqGattServerListen /* 195 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqGattServerListen = reqGattServerListen(parcel.readInt() != 0);
                    parcel2.writeNoException();
                    parcel2.writeInt(reqGattServerListen ? 1 : 0);
                    return true;
                case TRANSACTION_reqGattServerSendResponse /* 196 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqGattServerSendResponse = reqGattServerSendResponse(parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.createByteArray());
                    parcel2.writeNoException();
                    parcel2.writeInt(reqGattServerSendResponse ? 1 : 0);
                    return true;
                case TRANSACTION_reqGattServerSendNotification /* 197 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean reqGattServerSendNotification = reqGattServerSendNotification(parcel.readString(), parcel.readInt(), parcel.readInt() != 0 ? (ParcelUuid) ParcelUuid.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0 ? (ParcelUuid) ParcelUuid.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0, parcel.createByteArray());
                    parcel2.writeNoException();
                    parcel2.writeInt(reqGattServerSendNotification ? 1 : 0);
                    return true;
                case TRANSACTION_getGattAddedGattServiceUuidList /* 198 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    List<ParcelUuid> gattAddedGattServiceUuidList = getGattAddedGattServiceUuidList();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(gattAddedGattServiceUuidList);
                    return true;
                case TRANSACTION_getGattAddedGattCharacteristicUuidList /* 199 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    List<ParcelUuid> gattAddedGattCharacteristicUuidList = getGattAddedGattCharacteristicUuidList(parcel.readInt() != 0 ? (ParcelUuid) ParcelUuid.CREATOR.createFromParcel(parcel) : null);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(gattAddedGattCharacteristicUuidList);
                    return true;
                case 200:
                    parcel.enforceInterface(DESCRIPTOR);
                    List<ParcelUuid> gattAddedGattDescriptorUuidList = getGattAddedGattDescriptorUuidList(parcel.readInt() != 0 ? (ParcelUuid) ParcelUuid.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0 ? (ParcelUuid) ParcelUuid.CREATOR.createFromParcel(parcel) : null);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(gattAddedGattDescriptorUuidList);
                    return true;
                case 201:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean registerUsbCallback = registerUsbCallback(UiCallbackUsbBluetooth.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    parcel2.writeInt(registerUsbCallback ? 1 : 0);
                    return true;
                case 202:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean unregisterUsbCallback = unregisterUsbCallback(UiCallbackUsbBluetooth.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    parcel2.writeInt(unregisterUsbCallback ? 1 : 0);
                    return true;
                case 203:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean usbSearch = usbSearch();
                    parcel2.writeNoException();
                    parcel2.writeInt(usbSearch ? 1 : 0);
                    return true;
                case 204:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean usbStopSearch = usbStopSearch();
                    parcel2.writeNoException();
                    parcel2.writeInt(usbStopSearch ? 1 : 0);
                    return true;
                case 205:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean usbConnect = usbConnect(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(usbConnect ? 1 : 0);
                    return true;
                case 206:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean usbDisConnect = usbDisConnect();
                    parcel2.writeNoException();
                    parcel2.writeInt(usbDisConnect ? 1 : 0);
                    return true;
                case 207:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean usbReqUnpair = usbReqUnpair(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(usbReqUnpair ? 1 : 0);
                    return true;
                case 208:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean usbEnabled = setUsbEnabled(parcel.readInt() != 0);
                    parcel2.writeNoException();
                    parcel2.writeInt(usbEnabled ? 1 : 0);
                    return true;
                case 209:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean isUsbEnabled = isUsbEnabled();
                    parcel2.writeNoException();
                    parcel2.writeInt(isUsbEnabled ? 1 : 0);
                    return true;
                case 210:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean isUsbDiscovering = isUsbDiscovering();
                    parcel2.writeNoException();
                    parcel2.writeInt(isUsbDiscovering ? 1 : 0);
                    return true;
                case 211:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean isUsbHfpConnected = isUsbHfpConnected(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(isUsbHfpConnected ? 1 : 0);
                    return true;
                case 212:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean isUsbA2dpConnected = isUsbA2dpConnected(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(isUsbA2dpConnected ? 1 : 0);
                    return true;
                case 213:
                    parcel.enforceInterface(DESCRIPTOR);
                    int usbConnectionState = getUsbConnectionState(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(usbConnectionState);
                    return true;
                case TRANSACTION_isUsbAvrcpConnected /* 214 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean isUsbAvrcpConnected = isUsbAvrcpConnected(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(isUsbAvrcpConnected ? 1 : 0);
                    return true;
                case TRANSACTION_reqUsbBtPairedDevices /* 215 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    List<UsbBluetoothDevice> reqUsbBtPairedDevices = reqUsbBtPairedDevices();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(reqUsbBtPairedDevices);
                    return true;
                case TRANSACTION_getUsbBtLocalName /* 216 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    String usbBtLocalName = getUsbBtLocalName();
                    parcel2.writeNoException();
                    parcel2.writeString(usbBtLocalName);
                    return true;
                case TRANSACTION_setUsbBtLocalName /* 217 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean usbBtLocalName2 = setUsbBtLocalName(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(usbBtLocalName2 ? 1 : 0);
                    return true;
                case TRANSACTION_getUsbAddress /* 218 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    String usbAddress = getUsbAddress();
                    parcel2.writeNoException();
                    parcel2.writeString(usbAddress);
                    return true;
                case TRANSACTION_getUsbConnectedDevice /* 219 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    String usbConnectedDevice = getUsbConnectedDevice();
                    parcel2.writeNoException();
                    parcel2.writeString(usbConnectedDevice);
                    return true;
                case TRANSACTION_getLocalUsbConnectionState /* 220 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    int localUsbConnectionState = getLocalUsbConnectionState();
                    parcel2.writeNoException();
                    parcel2.writeInt(localUsbConnectionState);
                    return true;
                case TRANSACTION_getProfileConnectionState /* 221 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    int profileConnectionState = getProfileConnectionState(parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeInt(profileConnectionState);
                    return true;
                case TRANSACTION_usbDisConnectDevice /* 222 */:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean usbDisConnectDevice = usbDisConnectDevice(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(usbDisConnectDevice ? 1 : 0);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes.dex */
        public static class Proxy implements UiCommand {
            private IBinder mRemote;

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public String getUiServiceVersionName() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean isAvrcpServiceReady() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean isA2dpServiceReady() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean isSppServiceReady() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean isBluetoothServiceReady() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean isHfpServiceReady() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean isHidServiceReady() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean isPbapServiceReady() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean isOppServiceReady() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean isMapServiceReady() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean registerA2dpCallback(UiCallbackA2dp uiCallbackA2dp) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(uiCallbackA2dp != null ? uiCallbackA2dp.asBinder() : null);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean unregisterA2dpCallback(UiCallbackA2dp uiCallbackA2dp) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(uiCallbackA2dp != null ? uiCallbackA2dp.asBinder() : null);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public int getA2dpConnectionState() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean isA2dpConnected() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public String getA2dpConnectedAddress() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqA2dpConnect(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqA2dpDisconnect(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public void pauseA2dpRender() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public void startA2dpRender() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean setA2dpLocalVolume(float f) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeFloat(f);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean setA2dpStreamType(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public int getA2dpStreamType() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean registerAvrcpCallback(UiCallbackAvrcp uiCallbackAvrcp) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(uiCallbackAvrcp != null ? uiCallbackAvrcp.asBinder() : null);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean unregisterAvrcpCallback(UiCallbackAvrcp uiCallbackAvrcp) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(uiCallbackAvrcp != null ? uiCallbackAvrcp.asBinder() : null);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public int getAvrcpConnectionState() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean isAvrcpConnected() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public String getAvrcpConnectedAddress() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqAvrcpConnect(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqAvrcpDisconnect(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean isAvrcp13Supported(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean isAvrcp14Supported(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqAvrcpPlay() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqAvrcpStop() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqAvrcpPause() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqAvrcpForward() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqAvrcpBackward() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqAvrcpVolumeUp() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqAvrcpVolumeDown() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqAvrcpStartFastForward() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqAvrcpStopFastForward() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqAvrcpStartRewind() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqAvrcpStopRewind() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqAvrcp13GetCapabilitiesSupportEvent() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqAvrcp13GetPlayerSettingAttributesList() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqAvrcp13GetPlayerSettingValuesList(byte b) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByte(b);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqAvrcp13GetPlayerSettingCurrentValues() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqAvrcp13SetPlayerSettingValue(byte b, byte b2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByte(b);
                    obtain.writeByte(b2);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqAvrcp13GetElementAttributesPlaying() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqAvrcp13GetPlayStatus() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqAvrcpRegisterEventWatcher(byte b, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByte(b);
                    obtain.writeLong(j);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqAvrcpUnregisterEventWatcher(byte b) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByte(b);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqAvrcp13NextGroup() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqAvrcp13PreviousGroup() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean isAvrcp14BrowsingChannelEstablished() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqAvrcp14SetAddressedPlayer(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqAvrcp14SetBrowsedPlayer(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqAvrcp14GetFolderItems(byte b) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByte(b);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqAvrcp14ChangePath(int i, long j, byte b) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeByte(b);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqAvrcp14GetItemAttributes(byte b, int i, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByte(b);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqAvrcp14PlaySelectedItem(byte b, int i, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByte(b);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqAvrcp14Search(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqAvrcp14AddToNowPlaying(byte b, int i, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByte(b);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    this.mRemote.transact(62, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqAvrcp14SetAbsoluteVolume(byte b) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByte(b);
                    this.mRemote.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean registerBtCallback(UiCallbackBluetooth uiCallbackBluetooth) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(uiCallbackBluetooth != null ? uiCallbackBluetooth.asBinder() : null);
                    this.mRemote.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean unregisterBtCallback(UiCallbackBluetooth uiCallbackBluetooth) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(uiCallbackBluetooth != null ? uiCallbackBluetooth.asBinder() : null);
                    this.mRemote.transact(65, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public String getNfServiceVersionName() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(66, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean setBtEnable(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(67, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean setBtDiscoverableTimeout(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(68, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean startBtDiscovery() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(69, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean cancelBtDiscovery() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(70, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqBtPair(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(71, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqBtUnpair(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(72, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqBtPairedDevices() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(73, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public String getBtLocalName() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(74, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public String getBtRemoteDeviceName(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(75, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public String getBtLocalAddress() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(76, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean setBtLocalName(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(77, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean isBtEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(78, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public int getBtState() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(79, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean isBtDiscovering() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(80, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean isBtDiscoverable() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(81, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean isBtAutoConnectEnable() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(82, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public int reqBtConnectHfpA2dp(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(83, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public int reqBtDisconnectAll() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(84, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public int getBtRemoteUuids(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(85, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean switchBtRoleMode(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(86, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public int getBtRoleMode() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(87, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public void setBtAutoConnect(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(88, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public int getBtAutoConnectCondition() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(89, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public int getBtAutoConnectPeriod() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(90, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public int getBtAutoConnectState() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(91, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public String getBtAutoConnectingAddress() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(92, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean registerHfpCallback(UiCallbackHfp uiCallbackHfp) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(uiCallbackHfp != null ? uiCallbackHfp.asBinder() : null);
                    this.mRemote.transact(93, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean unregisterHfpCallback(UiCallbackHfp uiCallbackHfp) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(uiCallbackHfp != null ? uiCallbackHfp.asBinder() : null);
                    this.mRemote.transact(94, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public int getHfpConnectionState() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(95, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean isHfpConnected() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(96, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public String getHfpConnectedAddress() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(97, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public int getHfpAudioConnectionState() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(98, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqHfpConnect(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(99, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqHfpDisconnect(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(100, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public int getHfpRemoteSignalStrength() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(101, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public List<NfHfpClientCall> getHfpCallList() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(102, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(NfHfpClientCall.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean isHfpRemoteOnRoaming() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(103, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public int getHfpRemoteBatteryIndicator() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(104, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean isHfpRemoteTelecomServiceOn() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(105, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean isHfpRemoteVoiceDialOn() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(106, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqHfpDialCall(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(107, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqHfpReDial() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(108, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqHfpMemoryDial(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(109, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqHfpAnswerCall(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(110, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqHfpRejectIncomingCall() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(111, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqHfpTerminateCurrentCall() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(112, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqHfpSendDtmf(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(113, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqHfpAudioTransferToCarkit() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(114, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqHfpAudioTransferToPhone() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(115, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public String getHfpRemoteNetworkOperator() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(116, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public String getHfpRemoteSubscriberNumber() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(117, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqHfpVoiceDial(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(118, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public void pauseHfpRender() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(119, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public void startHfpRender() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(120, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean isHfpMicMute() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(121, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public void muteHfpMic(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(122, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean isHfpInBandRingtoneSupport() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(123, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean registerPbapCallback(UiCallbackPbap uiCallbackPbap) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(uiCallbackPbap != null ? uiCallbackPbap.asBinder() : null);
                    this.mRemote.transact(124, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean unregisterPbapCallback(UiCallbackPbap uiCallbackPbap) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(uiCallbackPbap != null ? uiCallbackPbap.asBinder() : null);
                    this.mRemote.transact(125, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public int getPbapConnectionState() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(126, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean isPbapDownloading() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_isPbapDownloading, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public String getPbapDownloadingAddress() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(128, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqPbapDownload(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(129, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqPbapDownloadRange(String str, int i, int i2, int i3, int i4) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(130, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqPbapDownloadToDatabase(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(131, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqPbapDownloadRangeToDatabase(String str, int i, int i2, int i3, int i4) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(132, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqPbapDownloadToContactsProvider(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(133, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqPbapDownloadRangeToContactsProvider(String str, int i, int i2, int i3, int i4) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(134, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public void reqPbapDatabaseQueryNameByNumber(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(135, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public void reqPbapDatabaseQueryNameByPartialNumber(String str, String str2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(136, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public void reqPbapDatabaseAvailable(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(137, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public void reqPbapDeleteDatabaseByAddress(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(138, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public void reqPbapCleanDatabase() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(139, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqPbapDownloadInterrupt(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(140, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean setPbapDownloadNotify(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(141, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean registerSppCallback(UiCallbackSpp uiCallbackSpp) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(uiCallbackSpp != null ? uiCallbackSpp.asBinder() : null);
                    this.mRemote.transact(142, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean unregisterSppCallback(UiCallbackSpp uiCallbackSpp) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(uiCallbackSpp != null ? uiCallbackSpp.asBinder() : null);
                    this.mRemote.transact(Stub.TRANSACTION_unregisterSppCallback, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqSppConnect(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(Stub.TRANSACTION_reqSppConnect, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqSppDisconnect(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(145, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public void reqSppConnectedDeviceAddressList() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_reqSppConnectedDeviceAddressList, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean isSppConnected(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(Stub.TRANSACTION_isSppConnected, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public void reqSppSendData(String str, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(148, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean registerHidCallback(UiCallbackHid uiCallbackHid) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(uiCallbackHid != null ? uiCallbackHid.asBinder() : null);
                    this.mRemote.transact(Stub.TRANSACTION_registerHidCallback, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean unregisterHidCallback(UiCallbackHid uiCallbackHid) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(uiCallbackHid != null ? uiCallbackHid.asBinder() : null);
                    this.mRemote.transact(150, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public int getHidConnectionState() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_getHidConnectionState, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean isHidConnected() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_isHidConnected, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public String getHidConnectedAddress() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_getHidConnectedAddress, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqHidConnect(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(Stub.TRANSACTION_reqHidConnect, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqHidDisconnect(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(155, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqSendHidMouseCommand(int i, int i2, int i3, int i4) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(Stub.TRANSACTION_reqSendHidMouseCommand, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqSendHidVirtualKeyCommand(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(Stub.TRANSACTION_reqSendHidVirtualKeyCommand, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean registerMapCallback(UiCallbackMap uiCallbackMap) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(uiCallbackMap != null ? uiCallbackMap.asBinder() : null);
                    this.mRemote.transact(Stub.TRANSACTION_registerMapCallback, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean unregisterMapCallback(UiCallbackMap uiCallbackMap) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(uiCallbackMap != null ? uiCallbackMap.asBinder() : null);
                    this.mRemote.transact(Stub.TRANSACTION_unregisterMapCallback, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqMapDownloadSingleMessage(String str, int i, String str2, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeInt(i2);
                    this.mRemote.transact(160, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqMapDownloadMessage(String str, int i, boolean z, int i2, int i3, int i4, String str2, String str3, String str4, String str5, int i5, int i6) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(z ? 1 : 0);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeString(str5);
                    obtain.writeInt(i5);
                    obtain.writeInt(i6);
                    this.mRemote.transact(Stub.TRANSACTION_reqMapDownloadMessage, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqMapRegisterNotification(String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(Stub.TRANSACTION_reqMapRegisterNotification, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public void reqMapUnregisterNotification(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(Stub.TRANSACTION_reqMapUnregisterNotification, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean isMapNotificationRegistered(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(Stub.TRANSACTION_isMapNotificationRegistered, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqMapDownloadInterrupt(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(Stub.TRANSACTION_reqMapDownloadInterrupt, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public void reqMapDatabaseAvailable() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_reqMapDatabaseAvailable, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public void reqMapDeleteDatabaseByAddress(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(Stub.TRANSACTION_reqMapDeleteDatabaseByAddress, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public void reqMapCleanDatabase() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_reqMapCleanDatabase, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public int getMapCurrentState(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(Stub.TRANSACTION_getMapCurrentState, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public int getMapRegisterState(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(170, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqMapSendMessage(String str, String str2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(Stub.TRANSACTION_reqMapSendMessage, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqMapDeleteMessage(String str, int i, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(Stub.TRANSACTION_reqMapDeleteMessage, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqMapChangeReadStatus(String str, int i, String str2, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(Stub.TRANSACTION_reqMapChangeReadStatus, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean setMapDownloadNotify(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(Stub.TRANSACTION_setMapDownloadNotify, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean registerOppCallback(UiCallbackOpp uiCallbackOpp) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(uiCallbackOpp != null ? uiCallbackOpp.asBinder() : null);
                    this.mRemote.transact(Stub.TRANSACTION_registerOppCallback, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean unregisterOppCallback(UiCallbackOpp uiCallbackOpp) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(uiCallbackOpp != null ? uiCallbackOpp.asBinder() : null);
                    this.mRemote.transact(Stub.TRANSACTION_unregisterOppCallback, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean setOppFilePath(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(Stub.TRANSACTION_setOppFilePath, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public String getOppFilePath() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_getOppFilePath, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqOppAcceptReceiveFile(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(Stub.TRANSACTION_reqOppAcceptReceiveFile, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqOppInterruptReceiveFile() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_reqOppInterruptReceiveFile, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public void setTargetAddress(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(Stub.TRANSACTION_setTargetAddress, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public String getTargetAddress() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_getTargetAddress, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public void reqAvrcpUpdateSongStatus() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_reqAvrcpUpdateSongStatus, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean isGattServiceReady() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_isGattServiceReady, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean registerGattServerCallback(UiCallbackGattServer uiCallbackGattServer) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(uiCallbackGattServer != null ? uiCallbackGattServer.asBinder() : null);
                    this.mRemote.transact(Stub.TRANSACTION_registerGattServerCallback, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean unregisterGattServerCallback(UiCallbackGattServer uiCallbackGattServer) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(uiCallbackGattServer != null ? uiCallbackGattServer.asBinder() : null);
                    this.mRemote.transact(Stub.TRANSACTION_unregisterGattServerCallback, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public int getGattServerConnectionState() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_getGattServerConnectionState, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqGattServerDisconnect(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(Stub.TRANSACTION_reqGattServerDisconnect, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqGattServerBeginServiceDeclaration(int i, ParcelUuid parcelUuid) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (parcelUuid != null) {
                        obtain.writeInt(1);
                        parcelUuid.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(Stub.TRANSACTION_reqGattServerBeginServiceDeclaration, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqGattServerAddCharacteristic(ParcelUuid parcelUuid, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelUuid != null) {
                        obtain.writeInt(1);
                        parcelUuid.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(Stub.TRANSACTION_reqGattServerAddCharacteristic, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqGattServerAddDescriptor(ParcelUuid parcelUuid, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelUuid != null) {
                        obtain.writeInt(1);
                        parcelUuid.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeInt(i);
                    this.mRemote.transact(Stub.TRANSACTION_reqGattServerAddDescriptor, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqGattServerEndServiceDeclaration() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(192, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqGattServerRemoveService(int i, ParcelUuid parcelUuid) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (parcelUuid != null) {
                        obtain.writeInt(1);
                        parcelUuid.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(Stub.TRANSACTION_reqGattServerRemoveService, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqGattServerClearServices() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_reqGattServerClearServices, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqGattServerListen(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(Stub.TRANSACTION_reqGattServerListen, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqGattServerSendResponse(String str, int i, int i2, int i3, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(Stub.TRANSACTION_reqGattServerSendResponse, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean reqGattServerSendNotification(String str, int i, ParcelUuid parcelUuid, ParcelUuid parcelUuid2, boolean z, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    if (parcelUuid != null) {
                        obtain.writeInt(1);
                        parcelUuid.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (parcelUuid2 != null) {
                        obtain.writeInt(1);
                        parcelUuid2.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeInt(z ? 1 : 0);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(Stub.TRANSACTION_reqGattServerSendNotification, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public List<ParcelUuid> getGattAddedGattServiceUuidList() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_getGattAddedGattServiceUuidList, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(ParcelUuid.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public List<ParcelUuid> getGattAddedGattCharacteristicUuidList(ParcelUuid parcelUuid) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelUuid != null) {
                        obtain.writeInt(1);
                        parcelUuid.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(Stub.TRANSACTION_getGattAddedGattCharacteristicUuidList, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(ParcelUuid.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public List<ParcelUuid> getGattAddedGattDescriptorUuidList(ParcelUuid parcelUuid, ParcelUuid parcelUuid2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelUuid != null) {
                        obtain.writeInt(1);
                        parcelUuid.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (parcelUuid2 != null) {
                        obtain.writeInt(1);
                        parcelUuid2.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(200, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(ParcelUuid.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean registerUsbCallback(UiCallbackUsbBluetooth uiCallbackUsbBluetooth) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(uiCallbackUsbBluetooth != null ? uiCallbackUsbBluetooth.asBinder() : null);
                    this.mRemote.transact(201, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean unregisterUsbCallback(UiCallbackUsbBluetooth uiCallbackUsbBluetooth) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(uiCallbackUsbBluetooth != null ? uiCallbackUsbBluetooth.asBinder() : null);
                    this.mRemote.transact(202, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean usbSearch() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(203, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean usbStopSearch() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(204, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean usbConnect(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(205, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean usbDisConnect() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(206, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean usbReqUnpair(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(207, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean setUsbEnabled(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(208, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean isUsbEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(209, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean isUsbDiscovering() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(210, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean isUsbHfpConnected(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(211, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean isUsbA2dpConnected(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(212, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public int getUsbConnectionState(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(213, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean isUsbAvrcpConnected(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(Stub.TRANSACTION_isUsbAvrcpConnected, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public List<UsbBluetoothDevice> reqUsbBtPairedDevices() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_reqUsbBtPairedDevices, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(UsbBluetoothDevice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public String getUsbBtLocalName() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_getUsbBtLocalName, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean setUsbBtLocalName(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(Stub.TRANSACTION_setUsbBtLocalName, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public String getUsbAddress() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_getUsbAddress, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public String getUsbConnectedDevice() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_getUsbConnectedDevice, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public int getLocalUsbConnectionState() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_getLocalUsbConnectionState, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public int getProfileConnectionState(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(Stub.TRANSACTION_getProfileConnectionState, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.nforetek.bt.aidl.UiCommand
            public boolean usbDisConnectDevice(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(Stub.TRANSACTION_usbDisConnectDevice, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
