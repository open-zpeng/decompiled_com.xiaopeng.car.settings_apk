package com.xiaopeng.car.settingslibrary.common.net;

import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import com.xiaopeng.car.settingslibrary.common.net.NetworkBoundResource;
import java.util.Objects;
/* loaded from: classes.dex */
public abstract class NetworkBoundResource<ResultType> {
    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();

    protected abstract LiveData<ApiResponse<ResultType>> createCall();

    protected abstract LiveData<ResultType> loadFromLocal();

    protected abstract void saveCallResult(ResultType resulttype);

    protected abstract boolean shouldFetch(ResultType resulttype);

    protected abstract boolean shouldSaveLocal();

    public NetworkBoundResource() {
        setValue(Resource.loading(null));
        final LiveData<ResultType> loadFromLocal = loadFromLocal();
        if (loadFromLocal == null || loadFromLocal.getValue() == null) {
            try {
                fetchFromNetwork(loadFromLocal);
                return;
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        this.result.addSource(loadFromLocal, new Observer() { // from class: com.xiaopeng.car.settingslibrary.common.net.-$$Lambda$NetworkBoundResource$kJTndmaFWD5WsERrP8e-Rgk5tL0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                NetworkBoundResource.this.lambda$new$1$NetworkBoundResource(loadFromLocal, obj);
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ void lambda$new$1$NetworkBoundResource(LiveData liveData, Object obj) {
        this.result.removeSource(liveData);
        if (shouldFetch(obj)) {
            try {
                fetchFromNetwork(liveData);
                return;
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        this.result.addSource(liveData, new Observer() { // from class: com.xiaopeng.car.settingslibrary.common.net.-$$Lambda$NetworkBoundResource$vmkMdYbMvqFuYVHrd8CbxgmFscU
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj2) {
                NetworkBoundResource.this.lambda$null$0$NetworkBoundResource(obj2);
            }
        });
    }

    public /* synthetic */ void lambda$null$0$NetworkBoundResource(Object obj) {
        setValue(Resource.success(obj));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setValue(Resource<ResultType> resource) {
        if (Objects.equals(this.result.getValue(), resource)) {
            return;
        }
        this.result.setValue(resource);
    }

    private void fetchFromNetwork(final LiveData<ResultType> liveData) {
        final LiveData<ApiResponse<ResultType>> createCall = createCall();
        if (liveData != null) {
            this.result.addSource(liveData, new Observer() { // from class: com.xiaopeng.car.settingslibrary.common.net.-$$Lambda$NetworkBoundResource$IggrzboVd4Nw9c_nU0YBCtOEGFc
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    NetworkBoundResource.this.lambda$fetchFromNetwork$2$NetworkBoundResource(obj);
                }
            });
        }
        this.result.addSource(createCall, new Observer() { // from class: com.xiaopeng.car.settingslibrary.common.net.-$$Lambda$NetworkBoundResource$iC1tGflkhBe2L_IW41j5Ic2Q0io
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                NetworkBoundResource.this.lambda$fetchFromNetwork$5$NetworkBoundResource(createCall, liveData, (ApiResponse) obj);
            }
        });
    }

    public /* synthetic */ void lambda$fetchFromNetwork$2$NetworkBoundResource(Object obj) {
        setValue(Resource.loading(obj));
    }

    public /* synthetic */ void lambda$fetchFromNetwork$5$NetworkBoundResource(LiveData liveData, LiveData liveData2, final ApiResponse apiResponse) {
        this.result.removeSource(liveData);
        if (liveData2 != null) {
            this.result.removeSource(liveData2);
        }
        if (apiResponse.isSuccessful()) {
            if (shouldSaveLocal()) {
                saveResultAndReInit(apiResponse);
                return;
            } else {
                this.result.addSource(liveData, new Observer() { // from class: com.xiaopeng.car.settingslibrary.common.net.-$$Lambda$NetworkBoundResource$DNgbkRRnnmbYPnVdFtttAQilAYg
                    @Override // androidx.lifecycle.Observer
                    public final void onChanged(Object obj) {
                        NetworkBoundResource.this.lambda$null$3$NetworkBoundResource((ApiResponse) obj);
                    }
                });
                return;
            }
        }
        onFetchFailed(liveData);
        if (liveData2 == null || liveData2.getValue() == null) {
            return;
        }
        this.result.addSource(liveData2, new Observer() { // from class: com.xiaopeng.car.settingslibrary.common.net.-$$Lambda$NetworkBoundResource$bb6Km_v2epzsELvef5o12q6VwhE
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                NetworkBoundResource.this.lambda$null$4$NetworkBoundResource(apiResponse, obj);
            }
        });
    }

    public /* synthetic */ void lambda$null$3$NetworkBoundResource(ApiResponse apiResponse) {
        setValue(Resource.success(apiResponse.body));
    }

    public /* synthetic */ void lambda$null$4$NetworkBoundResource(ApiResponse apiResponse, Object obj) {
        setValue(Resource.error(apiResponse.errorMessage, obj));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.car.settingslibrary.common.net.NetworkBoundResource$1  reason: invalid class name */
    /* loaded from: classes.dex */
    public class AnonymousClass1 extends AsyncTask<Void, Void, Void> {
        final /* synthetic */ ApiResponse val$response;

        AnonymousClass1(ApiResponse apiResponse) {
            this.val$response = apiResponse;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Void doInBackground(Void... voidArr) {
            NetworkBoundResource.this.saveCallResult(this.val$response.body);
            return null;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(Void r3) {
            NetworkBoundResource.this.result.addSource(NetworkBoundResource.this.loadFromLocal(), new Observer() { // from class: com.xiaopeng.car.settingslibrary.common.net.-$$Lambda$NetworkBoundResource$1$ZjusUNHPCvpYIp8P2UmPm3fROxc
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    NetworkBoundResource.AnonymousClass1.this.lambda$onPostExecute$0$NetworkBoundResource$1(obj);
                }
            });
        }

        public /* synthetic */ void lambda$onPostExecute$0$NetworkBoundResource$1(Object obj) {
            NetworkBoundResource.this.setValue(Resource.success(obj));
        }
    }

    private void saveResultAndReInit(ApiResponse<ResultType> apiResponse) {
        new AnonymousClass1(apiResponse).execute(new Void[0]);
    }

    protected void onFetchFailed(LiveData<ApiResponse<ResultType>> liveData) {
        this.result.addSource(liveData, new Observer() { // from class: com.xiaopeng.car.settingslibrary.common.net.-$$Lambda$NetworkBoundResource$f_wegtMYx4atGOwQKJTG24z3HMU
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                NetworkBoundResource.this.lambda$onFetchFailed$6$NetworkBoundResource((ApiResponse) obj);
            }
        });
    }

    public /* synthetic */ void lambda$onFetchFailed$6$NetworkBoundResource(ApiResponse apiResponse) {
        setValue(Resource.error(apiResponse.errorMessage, apiResponse.body));
    }

    public LiveData<Resource<ResultType>> asLiveData() {
        return this.result;
    }
}
