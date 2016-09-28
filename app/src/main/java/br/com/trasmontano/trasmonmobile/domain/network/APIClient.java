package br.com.trasmontano.trasmonmobile.domain.network;

import android.telecom.Call;
import android.widget.ListView;

import java.util.List;
import java.util.StringTokenizer;

import br.com.trasmontano.trasmonmobile.domain.model.Associado;
import br.com.trasmontano.trasmonmobile.domain.model.EntrevistaQualificada;
import br.com.trasmontano.trasmonmobile.domain.model.EntrevistaQualificadaCID;
import br.com.trasmontano.trasmonmobile.domain.model.EntrevistaQualificadaQuestao;
import br.com.trasmontano.trasmonmobile.domain.model.Foto;
import br.com.trasmontano.trasmonmobile.domain.model.ItemCarencia;
import br.com.trasmontano.trasmonmobile.domain.model.UltimasConsultas;
import br.com.trasmontano.trasmonmobile.domain.model.Usuario;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * Created by fmuniz on 16/06/2016.
 */
public class APIClient {
    private static RestAdapter REST_ADAPTER;

    private static void createAdapterIfNeeded(){
        if(REST_ADAPTER == null){
            REST_ADAPTER = new RestAdapter.Builder()
                    .setEndpoint("http://m.trasmontano.com.br")
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setClient(new OkClient())
                    .build();
        }
    }

    public APIClient(){
        createAdapterIfNeeded();
    }

    public RestServices getRestService(){
        return REST_ADAPTER.create(RestServices.class);
    }

    public interface RestServices {
        //AUDITORIA
        @GET("/auditoria/informacaoassociado/{matricula}/{dependente}")
        void getInformacaoAssociado(
                @Path("matricula") String matricula,
                @Path("dependente") String dependente,
                Callback<Associado> callbackInformacaoAssociado
        );

        @GET("/auditoria/buscarfoto/{matricula}/{dependente}")
        void getFotoAssociado(
                @Path("matricula") String matricula,
                @Path("dependente") String dependente,
                Callback<Foto> callbackFotoAssociado
        );

        @GET("/auditoria/entrevistaqualificadaids/{matricula}/{dependente}")
        void getEntrevistaQualificadaIds(
                @Path("matricula") String matricula,
                @Path("dependente") String dependente,
                Callback<List<Integer>> callbackEntrevistaQualificadaIds
        );

        @GET("/auditoria/entrevistaqualificada/{id}")
        void getEntrevistaQualificada(
                @Path("id") int id,
                Callback<EntrevistaQualificada> callbackEntrevistaQualificada
        );

        //ACESSO
        @GET("/acesso/autenticacao")
        void getUsuario(
                @Header("usuario") String usuario,
                @Header("senha") String senha,
                Callback<Usuario> callbackUsuario
        );

        //ASSOCIADO
        @GET("/associado/carencia/{matricula}/{dependente}")
        void getCarencias(
                @Path("matricula") String matricula,
                @Path("dependente") String dependente,
                Callback<List<ItemCarencia>> callbackCarenciasAssociado
        );

        @GET("/associado/ultimasconsultas/{matricula}/{dependente}")
        void getUltimasConsultas(
                @Path("matricula") String matricula,
                @Path("dependente") String dependente,
                Callback<List<UltimasConsultas>> callbackUltimasConsultas
        );


        /*@FormUrlEncoded()
        @POST("/produto")
        void createProduto(
                @Field("id") String codigoBarras,
                @Field("descricao") String descricao,
                @Field("unidade") String unidade,
                @Field("preco") double preco,
                @Field("foto") String foto,
                @Field("ativo") int ativo,
                @Field("latitude") double latitude,
                @Field("longitude") double longitude,
                Callback<String> callbackCreateProduto
        );*/

        /*@FormUrlEncoded()
        @PUT("/produto")
        void updateProduto(
                @Field("id") String codigoBarras,
                @Field("descricao") String descricao,
                @Field("unidade") String unidade,
                @Field("preco") double preco,
                @Field("foto") String foto,
                @Field("ativo") int ativo,
                @Field("latitude") double latitude,
                @Field("longitude") double longitude,
                Callback<String> callbackUpdateProduto
        );*/

        //@Headers("Content-Type: application/json")
        //@POST("/compra/cadastro")
        //void enviarCompra(
        //        @Body Compra compra,
        //        Callback<String> callbackCompra
        //);
    }
}