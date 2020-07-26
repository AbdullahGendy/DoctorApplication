package com.company.doctorapplication.Network;


import com.company.doctorapplication.Models.AddNewClinic.AddNewClinicRequest;
import com.company.doctorapplication.Models.AddNewClinic.AddNewClinicResponse;
import com.company.doctorapplication.Models.Appointments.GetAppointmentsResponseModel;
import com.company.doctorapplication.Models.ChangeDoctorPassword.ChangeDoctorPasswordRequest;
import com.company.doctorapplication.Models.ChangeDoctorPassword.ChangeDoctorPasswordResponse;
import com.company.doctorapplication.Models.ConfirmReservation.ConfirmReservationRequest;
import com.company.doctorapplication.Models.ConfirmReservation.ConfirmReservationResponse;
import com.company.doctorapplication.Models.DeleteClinic.DeleteClinicResponse;
import com.company.doctorapplication.Models.DeviceToken.UpdateDeviceTokenRequest;
import com.company.doctorapplication.Models.DeviceToken.UpdateDeviceTokenResponse;
import com.company.doctorapplication.Models.GetClinicInfoByClinicId.GetClinicInfoByClinicIdResponse;
import com.company.doctorapplication.Models.GetClinics.GetClinicsResponse;
import com.company.doctorapplication.Models.GetDoctorInfo.GetDoctorInfoResponse;
import com.company.doctorapplication.Models.GetEstimatedReservationTime.GetEstimatedReservationTimeRequest;
import com.company.doctorapplication.Models.GetEstimatedReservationTime.GetEstimatedReservationTimeResponse;
import com.company.doctorapplication.Models.Login.LoginRequestModel;
import com.company.doctorapplication.Models.Login.LoginResponseModel;
import com.company.doctorapplication.Models.Reviews.ReviewsResponse;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

public interface RetrofitInterface {


    @GET("DoctorAccount/GetDoctorInfoByDoctorId/{id}")
    Observable<GetDoctorInfoResponse> GetDoctorInfoByDoctorId(@Path("id") String id);

    @POST("DoctorAccount/Login")
    Observable<LoginResponseModel> Login(@Body LoginRequestModel loginRequestModel);

    @POST("DoctorAccount/ChangeDoctorPassword")
    Observable<ChangeDoctorPasswordResponse> ChangeDoctorPassword(@Body ChangeDoctorPasswordRequest changeDoctorPasswordRequest);

    @POST("Clinic/AddClinic")
    Observable<AddNewClinicResponse> AddClinic(@Body AddNewClinicRequest addNewClinicRequest);

    @GET("RateAndReview/GetAllReviewsByDoctorId/1")
    Observable<ReviewsResponse> GetAllReviews();

    @POST("Reservation/GetEstimatedReservationTime")
    Observable<GetEstimatedReservationTimeResponse> GetEstimatedReservationTime
            (@Body GetEstimatedReservationTimeRequest getEstimatedReservationTimeRequest);

    @POST("Reservation/ConfirmReservation")
    Observable<ConfirmReservationResponse> ConfirmReservation(@Body ConfirmReservationRequest confirmReservationRequest);

    @GET("Clinic/GetClinicsByDoctorId/1")
    Observable<GetClinicsResponse> GetClinics();

    @DELETE("Clinic/DeleteClinicByClinicId/{id}")
    Observable<DeleteClinicResponse> DeleteClinic(@Path("id") String id);

    @GET("Clinic/GetClinicInfoByClinicId/{id}")
    Observable<GetClinicInfoByClinicIdResponse> GetClinicInfoByClinicId(@Path("id") String id);

    @GET("DoctorAccount/GetDoctorAppointmentsByDoctorId/1")
    Observable<GetAppointmentsResponseModel> GetAppointments();

    @PUT("DonatorAccount/UpdateDonatorDeviceToken")
    Observable<UpdateDeviceTokenResponse> UpdateDonatorDeviceToken(@Body UpdateDeviceTokenRequest updateDeviceTokenRequest);

}