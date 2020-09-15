
public class AdHolderOnline extends DialogUntil {
    public void setDebugMode(boolean isDebug) {
        Constants.isDebugMode = isDebug;
    }

    public void setListAd(HashMap<String, Stack<AdsChild>> mapAd) {
        Constants.map = mapAd;
    }

    private Activity activity;

    public interface AdHolderCallback {
        void onAdShow(@AdDef.NETWORK String network, @AdDef.AD_TYPE String adtype);

        void onAdClose(@AdDef.NETWORK String adType);

        void onAdFailToLoad(String messageError);

        void onAdOff();
    }

    public AdHolderOnline(Activity activity) {
        this.activity = activity;
    }

    public boolean isDebugMode(){
        return Constants.isDebugMode;
    }

    public void showAds(String spaceName, LinearLayout layoutContainer, String textLoading, AdHolderCallback callback) {
        if (!textLoading.equals("")) {
            showDialog(activity, textLoading);
        }

        new LoadData().getAdChild(activity, spaceName, new LoadData.ServerCallback() {
            @Override
            public void onLoadDone(Stack<AdsChild> list) {
                holderAds(list, layoutContainer, textLoading, callback);
            }

            @Override
            public void onLoadFail(String message) {
                if (!textLoading.equals("")) {
                    hideDialog();
                }
                if (message == null) message = "";
                if (Constants.isDebugMode) {
                    Toast.makeText(activity, "ADSERVER_ERROR: " + message, Toast.LENGTH_SHORT).show();
                }
                callback.onAdFailToLoad(message);

            }
        });
    }

    public void showAdsTotalOffline(HashMap<String, Stack<AdsChild>> map, String spaceName, LinearLayout layoutContainer, String textLoading, AdHolderCallback callback) {
        if (!textLoading.equals("")) {
            showDialog(activity, textLoading);
        }

        new CheckInternetConnection().runToCheckInternet(activity, new CheckInternetConnection.InternetCallback() {
            @Override
            public void onInernetAvailable() {
                Stack<AdsChild> list = map.get(spaceName);
                if (list != null) {
                    Stack<AdsChild> finalList = new Stack<>();
                    if (AdConstant.testAd == TestAd.GOOGLE) {
                        for (AdsChild adsChild : list) {
                            if (adsChild.getNetwork().equals(AdDef.NETWORK.GOOGLE)) {
                                finalList.add(adsChild);
                            }
                        }
                    } else if (AdConstant.testAd == TestAd.FACEBOOK) {
                        for (AdsChild adsChild : list) {
                            if (adsChild.getNetwork().equals(AdDef.NETWORK.FACEBOOK)) {
                                finalList.add(adsChild);
                            }
                        }
                    } else {
                        finalList.addAll(list);
//                        finalList = list;
                    }
                    holderAds(finalList, layoutContainer, textLoading, callback);
                }
            }

            @Override
            public void onInternetDisconnected(String messageError) {
                if (!textLoading.equals("")) {
                    hideDialog();
                }
                if (callback != null) callback.onAdFailToLoad(AdConstant.textNoInternet);
            }
        });


//        new LoadData().getAdChild(activity, spaceName, new LoadData.ServerCallback() {
//            @Override
//            public void onLoadDone(Stack<AdsChild> list) {
//                holderAds(list, layoutContainer, textLoading, callback);
//            }
//
//            @Override
//            public void onLoadFail(String message) {
//                if (!textLoading.equals("")) {
//                    hideDialog();
//                }
//                if (message == null) message = "";
//                if (Constants.isDebugMode) {
//                    Toast.makeText(activity, "ADSERVER_ERROR: " + message, Toast.LENGTH_SHORT).show();
//                }
//                callback.onAdFailToLoad(message);
//
//            }
//        });
    }

    public void showAdsTotalOffline(String spaceName, LinearLayout layoutContainer, String textLoading, AdHolderCallback callback) {
        if (!textLoading.equals("")) {
            showDialog(activity, textLoading);
        }

        new CheckInternetConnection().runToCheckInternet(activity, new CheckInternetConnection.InternetCallback() {
            @Override
            public void onInernetAvailable() {
                if (Constants.map == null) return;
                Stack<AdsChild> list = Constants.map.get(spaceName);
                if (list != null) {
                    Stack<AdsChild> finalList = new Stack<>();
                    if (AdConstant.testAd == TestAd.GOOGLE) {
                        for (AdsChild adsChild : list) {
                            if (adsChild.getNetwork().equals(AdDef.NETWORK.GOOGLE)) {
                                finalList.add(adsChild);
                            }
                        }
                    } else if (AdConstant.testAd == TestAd.FACEBOOK) {
                        for (AdsChild adsChild : list) {
                            if (adsChild.getNetwork().equals(AdDef.NETWORK.FACEBOOK)) {
                                finalList.add(adsChild);
                            }
                        }
                    } else {
                        finalList.addAll(list);
//                        finalList = list;
                    }
                    holderAds(finalList, layoutContainer, textLoading, callback);
                }
            }

            @Override
            public void onInternetDisconnected(String messageError) {
                if (!textLoading.equals("")) {
                    hideDialog();
                }
                if (callback != null) callback.onAdFailToLoad(AdConstant.textNoInternet);
            }
        });


//        new LoadData().getAdChild(activity, spaceName, new LoadData.ServerCallback() {
//            @Override
//            public void onLoadDone(Stack<AdsChild> list) {
//                holderAds(list, layoutContainer, textLoading, callback);
//            }
//
//            @Override
//            public void onLoadFail(String message) {
//                if (!textLoading.equals("")) {
//                    hideDialog();
//                }
//                if (message == null) message = "";
//                if (Constants.isDebugMode) {
//                    Toast.makeText(activity, "ADSERVER_ERROR: " + message, Toast.LENGTH_SHORT).show();
//                }
//                callback.onAdFailToLoad(message);
//
//            }
//        });
    }

    public void showAdsTotalOfflineLifecycle(String spaceName, LinearLayout layoutContainer, String textLoading, AdHolderCallback callback, LifecycleOwner lifecycleOwner) {
        if (!textLoading.equals("")) {
            showDialog(activity, textLoading);
        }
        lifecycleOwner.getLifecycle().addObserver(new LifecycleEventObserver() {
            @Override
            public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {

            }
        });
        new CheckInternetConnection().runToCheckInternet(activity, new CheckInternetConnection.InternetCallback() {
            @Override
            public void onInernetAvailable() {
                if (Constants.map == null) return;
                Stack<AdsChild> list = Constants.map.get(spaceName);
                if (list != null) {
                    Stack<AdsChild> finalList = new Stack<>();
                    if (AdConstant.testAd == TestAd.GOOGLE) {
                        for (AdsChild adsChild : list) {
                            if (adsChild.getNetwork().equals(AdDef.NETWORK.GOOGLE)) {
                                finalList.add(adsChild);
                            }
                        }
                    } else if (AdConstant.testAd == TestAd.FACEBOOK) {
                        for (AdsChild adsChild : list) {
                            if (adsChild.getNetwork().equals(AdDef.NETWORK.FACEBOOK)) {
                                finalList.add(adsChild);
                            }
                        }
                    } else {
                        finalList.addAll(list);
//                        finalList = list;
                    }
                    holderAds(finalList, layoutContainer, textLoading, callback);
                }
            }

            @Override
            public void onInternetDisconnected(String messageError) {
                if (!textLoading.equals("")) {
                    hideDialog();
                }
                if (callback != null) callback.onAdFailToLoad(AdConstant.textNoInternet);
            }
        });
    }

    public void showAdsWithOfflineId(Stack<AdsChild> offlineList, String spaceName, LinearLayout layoutContainer, String textLoading, AdHolderCallback callback) {
        if (!textLoading.equals("")) {
            showDialog(activity, textLoading);
        }

        if (offlineList != null && offlineList.size() > 0) {
            holderAds(offlineList, layoutContainer, textLoading, callback);
        }

//        new LoadData().getAdChild(activity, spaceName, new LoadData.ServerCallback() {
//            @Override
//            public void onLoadDone(Stack<AdsChild> list) {
//                holderAds(list, layoutContainer, textLoading, callback);
//            }
//
//            @Override
//            public void onLoadFail(String message) {
//                if (message == null) message = "";
//                if (Constants.isDebugMode) {
//                    Toast.makeText(activity, "ADSERVER_ERROR: " + message, Toast.LENGTH_SHORT).show();
//                }
//
//                if (offlineList != null && !offlineList.isEmpty() && !message.equals(AdConstant.textNoInternet)) {
//                    holderAds(offlineList, layoutContainer, textLoading, callback);
//                } else {
//                    if (!textLoading.equals("")) {
//                        hideDialog();
//                    }
//                    callback.onAdFailToLoad(message);
//                }
//            }
//        });
    }

    private void holderAds(Stack<AdsChild> list, LinearLayout layoutContainer, String textLoading, AdHolderCallback callback) {
        if (!list.empty()) {
            AdsChild adsChild = list.pop();
            distributeAds(adsChild, list, layoutContainer, textLoading, callback);
        } else {
            if (!textLoading.equals("")) {
                hideDialog();
            }
            if (Constants.isDebugMode) {
                Toast.makeText(activity, "ADSERVER_ERROR: NOTHING_TO_SHOW", Toast.LENGTH_SHORT).show();
            }
            if (callback != null) {
                callback.onAdFailToLoad("NOTHING_TO_SHOW");
            }
        }
    }

    private void distributeAds(AdsChild adsChild, Stack<AdsChild> list, LinearLayout layoutContainer, String textLoading, AdHolderCallback callback) {
        if (adsChild.getNetwork().equals(AdDef.NETWORK.GOOGLE)) {
            showAdGoogle(adsChild, list, layoutContainer, textLoading, callback);
            return;
        }

        if (adsChild.getNetwork().equals(AdDef.NETWORK.FACEBOOK)) {
            showAdFacebook(adsChild, list, layoutContainer, textLoading, callback);
            return;
        }

        if (adsChild.getNetwork().equals(AdDef.NETWORK.APPLOVIN)) {
            return;
        }

        if (adsChild.getNetwork().equals(AdDef.NETWORK.CROSS)) {
            showAdCross(adsChild, list, layoutContainer, textLoading, callback);
        }

    }

    private void showAdCross(AdsChild adsChild, Stack<AdsChild> list, LinearLayout layoutContainer, String textLoading, AdHolderCallback callback) {
        new CrossHolder().controlAds(activity, layoutContainer, adsChild, new AdHolderCallback() {
            @Override
            public void onAdShow(String network, String adtype) {
                callback.onAdShow(AdDef.NETWORK.CROSS, adtype);
            }

            @Override
            public void onAdClose(String adType) {
                callback.onAdClose(AdDef.NETWORK.CROSS);
            }

            @Override
            public void onAdFailToLoad(String messageError) {
                holderAds(list, layoutContainer, textLoading, callback);
            }

            @Override
            public void onAdOff() {
                //NO ACTION REQUIRE HERE
            }
        });
    }


    private void showAdFacebook(AdsChild adsChild, Stack<AdsChild> list, LinearLayout layoutContainer, String textLoading, AdHolderCallback callback) {
        new FBHolder().controlAds(activity, layoutContainer, adsChild, textLoading, new AdHolderCallback() {
            @Override
            public void onAdShow(String network, String adtype) {
                if(adtype == null) adtype = "";
                if (callback != null) {
                    callback.onAdShow(AdDef.NETWORK.FACEBOOK, adtype);
                }
            }

            @Override
            public void onAdClose(String adType) {
                if (callback != null) {
                    callback.onAdClose(AdDef.NETWORK.FACEBOOK);
                }
            }

            @Override
            public void onAdFailToLoad(String messageError) {
                if (messageError != null) {
                    if (messageError.equals("Ad was re-loaded too frequently")) {
                        holderAds(list, layoutContainer, textLoading, callback);
                    } else {
                        if (!messageError.equals(AdConstant.textTimeOut)) {
                            holderAds(list, layoutContainer, textLoading, callback);
                        } else {
                            if (callback != null) {
                                actionAdFaildToLoad(callback, AdConstant.textTimeOut);
                            }
                        }
                    }
                } else {
                    if (callback != null) {
                        actionAdFaildToLoad(callback, "failed");
                    }
                }
            }

            @Override
            public void onAdOff() {
                //NO ACTION REQUIRE HERE
            }
        });
    }

    private boolean isFaild = false;

    private void actionAdFaildToLoad(AdHolderCallback callback, String mess) {
        if (!isFaild) {
            isFaild = true;
            callback.onAdFailToLoad(mess);
        }
    }

    private void showAdGoogle(AdsChild adsChild, Stack<AdsChild> list, LinearLayout layoutContainer, String textLoading, AdHolderCallback callback) {

        new AdmobHolder().controlAds(activity, layoutContainer, adsChild, textLoading, new AdHolderCallback() {
            @Override
            public void onAdShow(String network, String adType) {
                if(adType == null) adType = "";
                if(callback != null) {
                    callback.onAdShow(AdDef.NETWORK.GOOGLE, adType);
                }
            }

            @Override
            public void onAdClose(String adType) {
                callback.onAdClose(AdDef.NETWORK.GOOGLE);
            }

            @Override
            public void onAdFailToLoad(String messageError) {
                if (messageError != null && !messageError.equals(AdConstant.textTimeOut)) {
                    holderAds(list, layoutContainer, textLoading, callback);
                } else {
                    if (callback != null) {
                        actionAdFaildToLoad(callback, AdConstant.textTimeOut);
                    }
                }

            }

            @Override
            public void onAdOff() {
                //NO ACTION REQUIRE HERE
            }
        });
    }


}
