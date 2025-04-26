import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.WalletApp;
import ru.controller.WalletController;
import ru.dto.OperationType;
import ru.dto.WalletRequestDto;
import ru.dto.WalletResponseDto;
import ru.exception.InsufficientFundsException;
import ru.exception.NotFoundException;
import ru.service.WalletService;

import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WalletController.class)
@ContextConfiguration(classes = WalletApp.class)
class WalletControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private WalletService walletService;

    @Test
    @DisplayName("POST /api/v1/wallet - успешная операция пополнения")
    void shouldDepositSuccessfully() throws Exception {
        UUID walletId = UUID.randomUUID();
        WalletRequestDto requestDto = new WalletRequestDto(walletId, OperationType.DEPOSIT, 1000L);
        WalletResponseDto responseDto = new WalletResponseDto(walletId, 1000L);

        when(walletService.updateBalance(any(WalletRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/wallet")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(walletId.toString())))
                .andExpect(jsonPath("$.balance", greaterThanOrEqualTo(1000)));
    }

    @Test
    @DisplayName("GET /api/v1/wallet/{WALLET_UUID} - успешное получение баланса")
    void shouldGetBalanceSuccessfully() throws Exception {
        UUID walletId = UUID.randomUUID();
        WalletResponseDto responseDto = new WalletResponseDto(walletId, 5000L);

        when(walletService.getBalance(walletId)).thenReturn(responseDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/wallet/{id}", walletId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(walletId.toString())))
                .andExpect(jsonPath("$.balance", greaterThanOrEqualTo(5000)));
    }

    @Test
    @DisplayName("POST /api/v1/wallet - ошибка: недостаточно средств")
    void shouldReturnBadRequestWhenInsufficientFunds() throws Exception {
        UUID walletId = UUID.randomUUID();
        WalletRequestDto requestDto = new WalletRequestDto(walletId, OperationType.WITHDRAW, 1000000L);

        when(walletService.updateBalance(any(WalletRequestDto.class)))
                .thenThrow(new InsufficientFundsException("Недостаточно средств"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Недостаточно средств"));
    }

}