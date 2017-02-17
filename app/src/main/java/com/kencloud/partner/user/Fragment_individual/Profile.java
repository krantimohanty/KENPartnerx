package com.kencloud.partner.user.Fragment_individual;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.kencloud.partner.user.R;
import com.kencloud.partner.user.app_adapter.KYC_address_proof;
import com.kencloud.partner.user.app_adapter.KYC_identity_proof;
import com.kencloud.partner.user.app_model.KYC_address;
import com.kencloud.partner.user.app_model.KYC_id;
import com.kencloud.partner.user.app_util.FilePath;
import com.kencloud.partner.user.app_util.ValidationUtil;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class Profile extends Fragment
//        implements View.OnClickListener
{
    Context context;
    private String imgString = "";
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private View rootView;
    private static final int PICK_FILE_REQUEST = 1;
    private static final String TAG = Profile.class.getSimpleName();
    private String selectedFilePath;
    private String SERVER_URL = "Your url paste here";
    ImageView ivAttachment;
    Button bUpload;
    TextView tvFilePath,addKYC;
    ProgressDialog dialog;
    KYC_address_proof address_adapter;
    private List<KYC_address> addressList;
    KYC_identity_proof identity_adapter;
    private List<KYC_id> identityList;
    MaterialBetterSpinner idproof, addressproofSpinner, qualification, occupation, companytype;
    private Button choosefile;
    ViewPager viewPager;
    private AppCompatButton btnNext, btnSave;
    private RecyclerView addressRecycle, identityRecycle;
private AppCompatEditText mobile;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final String[] company_type = new String[]{
                "Private Limited", "Public Limited", "Proprietorship", "Parternership"

        };
        final String[] str_ocupation = new String[]{
                "House Wife", " Salaried", "Self Employeed", "Others"

        };
        final String[] str_qualification = new String[]{
                "10th", "12th", "Graduation", "Post Graduation", "Others"};

        final String[] id_proof = new String[]{
                "AADHAR", "PAN", "Voter ID", "Passport", "Driving Licence"};

        final String[] addres_proof = new String[]{
                "AADHAR", "Driving Licence", "Voter Id", "Telephone Bill", "Electricity Bill"};

        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);




//        Identity Recycle
        identityRecycle=(RecyclerView) rootView.findViewById(R.id.identity_recycle);
        identityRecycle.setHasFixedSize(true);
        identityList= new ArrayList<>();
        identity_adapter = new KYC_identity_proof(getContext(), identityList);
        LinearLayoutManager identity = new LinearLayoutManager(getContext());
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(CompanyActivity.this);
        identityRecycle.setLayoutManager(identity);
        identity.setOrientation(LinearLayoutManager.VERTICAL);
        identity.scrollToPosition(0);
//        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        identityRecycle.setItemAnimator(new DefaultItemAnimator());
        identityRecycle.setAdapter(identity_adapter);






        addressRecycle=(RecyclerView) rootView.findViewById(R.id.address_recycle);
        addressRecycle.setHasFixedSize(true);
        addressList=new ArrayList<>();
        address_adapter = new KYC_address_proof(getContext(), addressList);
        LinearLayoutManager emailLayout = new LinearLayoutManager(getContext());
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(CompanyActivity.this);
        addressRecycle.setLayoutManager(emailLayout);
        emailLayout.setOrientation(LinearLayoutManager.VERTICAL);
        emailLayout.scrollToPosition(0);
//        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        addressRecycle.setItemAnimator(new DefaultItemAnimator());
        addressRecycle.setAdapter(address_adapter);

        viewPager = (ViewPager) getActivity().findViewById(R.id.viewpager);

        //KYC

        addKYC=(TextView) rootView.findViewById(R.id.add_KYC);
        addKYC.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          final Dialog dialog;
                                          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                              dialog = new Dialog(getActivity(), android.R.style.Theme_Material_Light_Dialog_Alert);
                                          } else {
                                              dialog = new Dialog(getActivity());
                                          }
                                          dialog.setContentView(R.layout.dialog_kyc);
                                          dialog.setTitle("KYC Details");
                                          Button addressproof = (Button) dialog.findViewById(R.id.address_proof);
                                          Button identityproof = (Button) dialog.findViewById(R.id.identity_proof);
                                          addressproof.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {
                                                  final Dialog dialog1;
                                                  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                                      dialog1 = new Dialog(getActivity(), android.R.style.Theme_Material_Light_Dialog_Alert);
                                                  } else {
                                                      dialog1 = new Dialog(getActivity());
                                                  }
                                                  dialog1.setContentView(R.layout.dialog_kyc_address);
                                                  dialog1.setTitle("KYC Details(Address)");
                                                  final AppCompatEditText ref_address = (AppCompatEditText) dialog1.findViewById(R.id.ref_address);
                                                  addressproofSpinner = (MaterialBetterSpinner) dialog1.findViewById(R.id.address_proof);
                                                  ArrayAdapter<String> addressProofAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, addres_proof
                                                  );
                                                  addressProofAdapter.setDropDownViewResource(R.layout.spinner_item);
                                                  addressproofSpinner.setAdapter(addressProofAdapter);
                                                  Button save_e = (Button) dialog1.findViewById(R.id.save);
                                                  save_e.setOnClickListener(new View.OnClickListener() {
                                                      @Override
                                                      public void onClick(View v) {
                                                          String id = ref_address.getText().toString();
                                                          String type = addressproofSpinner.getText().toString();
                                                          if (type.equals("") || id.equals("")) {
                                                              Snackbar.make(rootView, "Please enter valid proof details", Snackbar.LENGTH_LONG).show();
                                                          } else if (type.equals("PAN")) {
                                                              ValidationUtil.isValidPanCard(id);
                                                              Snackbar.make(rootView, "Please enter valid PAN no", Snackbar.LENGTH_LONG).show();
                                                          } else {
                                                              KYC_address address = new KYC_address(type, id);
                                                              addressList.add(address);
                                                              dialog1.dismiss();
                                                              address_adapter.notifyDataSetChanged();
                                                              Toast.makeText(getActivity(), "Saved Successfully", Toast.LENGTH_SHORT).show();
                                                              dialog.dismiss();
                                                          }
                                                      }
                                                  });

                                                  Button cancel_e = (Button) dialog1.findViewById(R.id.cancel);
                                                  cancel_e.setOnClickListener(new View.OnClickListener() {
                                                      @Override
                                                      public void onClick(View v) {
                                                          dialog1.dismiss();
                                                      }
                                                  });

                                                  dialog1.show();
                                              }
                                          });

                                          dialog.show();

                                          identityproof.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {
                                                  final Dialog dialog2;
                                                  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                                      dialog2 = new Dialog(getActivity(), android.R.style.Theme_Material_Light_Dialog_Alert);
                                                  } else {
                                                      dialog2 = new Dialog(getActivity());
                                                  }
                                                  dialog2.setContentView(R.layout.dialog_kyc_identity);
                                                  dialog2.setTitle("KYC Details(Identity)");
                                                  final AppCompatEditText ref_id = (AppCompatEditText) dialog2.findViewById(R.id.ref_id);
                                                  idproof = (MaterialBetterSpinner) dialog2.findViewById(R.id.id_proof);
                                                  ArrayAdapter<String> idProofAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, id_proof
                                                  );
                                                  idProofAdapter.setDropDownViewResource(R.layout.spinner_item);
                                                  idproof.setAdapter(idProofAdapter);
                                                  Button save_e = (Button) dialog2.findViewById(R.id.save);
                                                  save_e.setOnClickListener(new View.OnClickListener() {
                                                      @Override
                                                      public void onClick(View v) {
                                                          String id = ref_id.getText().toString();
                                                          String type = idproof.getText().toString();
                                                          if (type.equals("") || id.equals("")) {
                                                              Snackbar.make(rootView, "Please enter valid proof details", Snackbar.LENGTH_LONG).show();
                                                          } else if (type.equals("PAN")) {
                                                              ValidationUtil.isValidPanCard(id);
                                                              Snackbar.make(rootView, "Please enter valid PAN no", Snackbar.LENGTH_LONG).show();
                                                          } else {
                                                              KYC_id identitty = new KYC_id(type, id);
                                                              identityList.add(identitty);
                                                              dialog2.dismiss();
                                                              identity_adapter.notifyDataSetChanged();
                                                              Toast.makeText(getActivity(), "Saved Successfully", Toast.LENGTH_SHORT).show();
                                                              dialog.dismiss();
                                                          }
                                                      }
                                                  });

                                                  Button cancel_e = (Button) dialog2.findViewById(R.id.cancel);
                                                  cancel_e.setOnClickListener(new View.OnClickListener() {
                                                      @Override
                                                      public void onClick(View v) {
                                                          dialog2.dismiss();
                                                      }
                                                  });

                                                  dialog2.show();
                                              }
                                          });

                                          dialog.show();


                                      }
                                  });

                btnSave = (AppCompatButton) rootView.findViewById(R.id.save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity().getApplicationContext(), "Saved Successfully", Toast.LENGTH_LONG).show();
            }
        });
        btnNext = (AppCompatButton) rootView.findViewById(R.id.next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewPager.setCurrentItem(3);

            }
        });

//        choosefile = (Button) rootView.findViewById(R.id.choosefile);
//        initView();
//        choosefile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
////                String folderPath = Environment.getExternalStorageDirectory() + "/pathTo/folder";
////
////                Intent intent = new Intent();
////                intent.setAction(Intent.ACTION_GET_CONTENT);
////                Uri myUri = Uri.parse(folderPath);
////                intent.setDataAndType(myUri, "file/*");
////                startActivity(intent);
//
//            }
//        });



        qualification = (MaterialBetterSpinner) rootView.findViewById(R.id.highest_qualification);

        // Initializing an ArrayAdapter
//        qualification
        ArrayAdapter<String> qualificationAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, str_qualification
        );
        qualificationAdapter.setDropDownViewResource(R.layout.spinner_item);
        qualification.setAdapter(qualificationAdapter);

//        occupation
        occupation = (MaterialBetterSpinner) rootView.findViewById(R.id.occupation);
        ArrayAdapter<String> occupationAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, str_ocupation
        );
        occupationAdapter.setDropDownViewResource(R.layout.spinner_item);
        occupation.setAdapter(occupationAdapter);
//        id proof
//        idproof = (MaterialBetterSpinner) rootView.findViewById(R.id.id_proof);
//        ArrayAdapter<String> idProofAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, id_proof
//        );
//        idProofAdapter.setDropDownViewResource(R.layout.spinner_item);
//        idproof.setAdapter(idProofAdapter);
//        Adress proof

//        addressproof = (MaterialBetterSpinner) rootView.findViewById(R.id.address_proof);
//        ArrayAdapter<String> addressProofAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, addres_proof
//        );
//        addressProofAdapter.setDropDownViewResource(R.layout.spinner_item);
//        addressproof.setAdapter(addressProofAdapter);

        companytype = (MaterialBetterSpinner) rootView.findViewById(R.id.company_type);
        ArrayAdapter<String> companyTypeAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, company_type
        );
        companyTypeAdapter.setDropDownViewResource(R.layout.spinner_item);
        companytype.setAdapter(companyTypeAdapter);

        return rootView;

    }

//    private void initView() {
////        tvFilePath = (TextView) rootView.findViewById(R.id.filepath);
//        choosefile = (Button) rootView.findViewById(R.id.choosefile);
//        tvFilePath.setOnClickListener(this);
//        choosefile.setOnClickListener(this);
//    }

//    @Override
//    public void onClick(View view) {
//        if (view == choosefile) {
//
//            //on attachment icon click
//            showFileChooser();
//        }


//        if (view == tvFilePath) {
//
//            //on upload button Click
//            if (selectedFilePath != null) {
//                dialog = ProgressDialog.show(getContext(), "", "Uploading File...", true);
//
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        //creating new thread to handle Http Operations
//                        uploadFile(selectedFilePath);
//                    }
//                }).start();
//            } else {
//                Toast.makeText(getContext(), "Please choose a File First", Toast.LENGTH_SHORT).show();
//            }
//
//        }
//
//    }

//    private void showFileChooser() {
//        Intent intent = new Intent();
//        //sets the select file to all types of files
//        intent.setType("*/*");
//        //allows to select data and return it
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        //starts new activity to select file and return data
//        startActivityForResult(Intent.createChooser(intent, "Choose File to Upload.."), PICK_FILE_REQUEST);
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_FILE_REQUEST) {
                if (data == null) {
                    //no data present
                    return;
                }


                Uri selectedFileUri = data.getData();
                selectedFilePath = FilePath.getPath(getContext(), selectedFileUri);
                Log.i(TAG, "Selected File Path:" + selectedFilePath);

                if (selectedFilePath != null && !selectedFilePath.equals("")) {
                    tvFilePath.setText(selectedFilePath);
                } else {
                    Toast.makeText(getContext(), "Cannot upload file to server", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    //android upload file to server
    public int uploadFile(final String selectedFilePath) {

        int serverResponseCode = 0;

        HttpURLConnection connection;
        DataOutputStream dataOutputStream;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";


        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File selectedFile = new File(selectedFilePath);


        String[] parts = selectedFilePath.split("/");
        final String fileName = parts[parts.length - 1];

        if (!selectedFile.isFile()) {
            dialog.dismiss();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    tvFilePath.setText("Source File Doesn't Exist: " + selectedFilePath);
                }
            });
            return 0;
        } else {
            try {
                FileInputStream fileInputStream = new FileInputStream(selectedFile);
                URL url = new URL(SERVER_URL);
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);//Allow Inputs
                connection.setDoOutput(true);//Allow Outputs
                connection.setUseCaches(false);//Don't use a cached Copy
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Connection", "Keep-Alive");
                connection.setRequestProperty("ENCTYPE", "multipart/form-data");
                connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                connection.setRequestProperty("uploaded_file", selectedFilePath);

                //creating new dataoutputstream
                dataOutputStream = new DataOutputStream(connection.getOutputStream());

                //writing bytes to data outputstream
                dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                        + selectedFilePath + "\"" + lineEnd);

                dataOutputStream.writeBytes(lineEnd);

                //returns no. of bytes present in fileInputStream
                bytesAvailable = fileInputStream.available();
                //selecting the buffer size as minimum of available bytes or 1 MB
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                //setting the buffer as byte array of size of bufferSize
                buffer = new byte[bufferSize];

                //reads bytes from FileInputStream(from 0th index of buffer to buffersize)
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                //loop repeats till bytesRead = -1, i.e., no bytes are left to read
                while (bytesRead > 0) {
                    //write the bytes read from inputstream
                    dataOutputStream.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }

                dataOutputStream.writeBytes(lineEnd);
                dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                serverResponseCode = connection.getResponseCode();
                String serverResponseMessage = connection.getResponseMessage();

                Log.i(TAG, "Server Response is: " + serverResponseMessage + ": " + serverResponseCode);

                //response code of 200 indicates the server status OK
                if (serverResponseCode == 200) {
                   new Thread(new Runnable() {
                        @Override
                        public void run() {
                            tvFilePath.setText("File Upload completed.\n\n You can see the uploaded file here: \n\n" + "http://coderefer.com/extras/uploads/" + fileName);
                        }
                    });
                }

                //closing the input and output streams
                fileInputStream.close();
                dataOutputStream.flush();
                dataOutputStream.close();


            } catch (FileNotFoundException e) {
                e.printStackTrace();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "File Not Found", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "URL error!", Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Cannot Read/Write File!", Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();
            return serverResponseCode;
        }

    }
}

