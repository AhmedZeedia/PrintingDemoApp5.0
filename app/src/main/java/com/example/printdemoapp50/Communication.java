package com.example.printdemoapp50;

import android.content.Context;

import androidx.core.util.Pair;

import com.starmicronics.stario.StarIOPort;
import com.starmicronics.stario.StarPrinterStatus;
import com.starmicronics.stario.StarResultCode;
import com.starmicronics.starioextension.IPeripheralCommandParser;

import java.util.List;
import java.util.Map;

@SuppressWarnings({"UnusedParameters", "UnusedAssignment", "WeakerAccess"})
public class Communication {

    public static class CommunicationResult {
        private Result mResult = Result.ErrorUnknown;
        private int    mCode   = StarResultCode.FAILURE;

        public CommunicationResult(Result result, int code) {
            mResult = result;
            mCode   = code;
        }

        public Result getResult() {
            return mResult;
        }

        public int getCode() {
            return mCode;
        }
    }

    public enum Result {
        Success,
        ErrorUnknown,
        ErrorOpenPort,
        ErrorBeginCheckedBlock,
        ErrorEndCheckedBlock,
        ErrorWritePort,
        ErrorReadPort,
    }

    public enum PresenterStatus {
        NoPaper,
        Loop,
        Hold,
        Retract,
        Eject
    }

    interface StatusCallback {
        void onStatus(StarPrinterStatus result);
    }

    interface FirmwareInformationCallback {
        void onFirmwareInformation(Map<String, String> firmwareInformationMap);
    }

    interface SerialNumberCallback {
        void onSerialNumber(CommunicationResult communicationResult, String serialNumber);
    }

    interface SendCallback {
        void onStatus(CommunicationResult communicationResult);
    }

    interface PrintRedirectionCallback {
        void onStatus(List<Pair<String, CommunicationResult>> communicationResultList);
    }

    interface PresenterStateCheckCallback {
        void onStatus(Communication.PresenterStatus presenterStatus, StarPrinterStatus status);
    }



    public static String getCommunicationResultMessage(CommunicationResult communicationResult) {
        StringBuilder builder = new StringBuilder();

        switch (communicationResult.getResult()) {
            case Success:
                builder.append("Success!");
                break;
            case ErrorOpenPort:
                builder.append("Fail to openPort");
                break;
            case ErrorBeginCheckedBlock:
                builder.append("Printer is offline (beginCheckedBlock)");
                break;
            case ErrorEndCheckedBlock:
                builder.append("Printer is offline (endCheckedBlock)");
                break;
            case ErrorReadPort:
                builder.append("Read port error (readPort)");
                break;
            case ErrorWritePort:
                builder.append("Write port error (writePort)");
                break;
            default:
                builder.append("Unknown error");
                break;
        }

        if (communicationResult.getResult() != Result.Success) {
            builder.append("\n\nError code: ");
            builder.append(communicationResult.getCode());

            if (communicationResult.getCode() == StarResultCode.FAILURE) {
                builder.append(" (Failed)");
            }
            else if (communicationResult.getCode() == StarResultCode.FAILURE_IN_USE) {
                builder.append(" (In use)");
            }
        }

        return builder.toString();
    }
}

