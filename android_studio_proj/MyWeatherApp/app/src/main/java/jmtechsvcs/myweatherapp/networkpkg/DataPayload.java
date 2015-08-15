package jmtechsvcs.myweatherapp.networkpkg;

import java.io.ByteArrayInputStream;

/**
 * Created by jimmy on 7/24/2015.
 */
/*
		this class is used to return the payload type to be used by the
		caller after getting network data.
	 */
public class DataPayload
{
    public enum T_Payload_Type
    {
        E_JSON_PAYLOAD_TYPE,
        E_BYTE_ARRAY_PAYLOAD_TYPE,
        E_BYTE_STREAM_PAYLOAD_TYPE
    }

    private byte [] bytePayload;
    private String stringPayload;
    private ByteArrayInputStream inputStreamPayload;
    private T_Payload_Type payloadType;

    public DataPayload(){
    }

    public byte[] getBytePayload(){
        return bytePayload;
    }

    public void setBytePayload(byte[] bytePayload){
        this.bytePayload = bytePayload;
    }

    public String getStringPayload(){
        return stringPayload;
    }

    public void setStringPayload(String stringPayload){
        this.stringPayload = stringPayload;
    }

    public T_Payload_Type getPayloadType(){
        return payloadType;
    }

    public void setPayloadType(T_Payload_Type payloadType){
        this.payloadType = payloadType;
    }

    public ByteArrayInputStream getInputStreamPayload(){
        return inputStreamPayload;
    }

    public void setInputStreamPayload(ByteArrayInputStream inputStreamPayload){
        this.inputStreamPayload = inputStreamPayload;
    }
}
