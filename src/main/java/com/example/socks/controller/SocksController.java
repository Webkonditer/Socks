package com.example.socks.controller;

import com.example.socks.model.Socks;
import com.example.socks.service.SocksService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.socks.service.SocksService.OPERATIONS;

@RestController
@RequestMapping("/api/socks")
public class SocksController {
    private final SocksService socksService;

    public SocksController(SocksService socksService) {
        this.socksService = socksService;
    }

    @Operation(
            summary = "Добавление носков на склад",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Носки добавлены на склад",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Ошибки в запросе"
                    )
            }, tags = "Api"
    )
    @PostMapping("/income")
    public ResponseEntity<?> addSocks(@RequestBody Socks socks) {
        if(socks.getColor().length() < 2 || socks.getCottonPart() < 0 || socks.getCottonPart() > 100 || socks.getQuantity() < 1) {
            return ResponseEntity.status(400).build();
        }
        socksService.addSocks(socks);
        return ResponseEntity.status(200).build();
    }

    @Operation(
            summary = "Изъятие носков со склада",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Носки изъяты со склада",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Ошибки в запросе"
                    )
            }, tags = "Api"
    )
    @PostMapping("/outcome")
    public ResponseEntity<?> takeSocks(@RequestBody Socks socks) {
        if(socks.getColor().length() < 2 || socks.getCottonPart() < 0 || socks.getCottonPart() > 100 || socks.getQuantity() < 1) {
            return ResponseEntity.status(400).build();
        }
        return socksService.takeSocks(socks) ? ResponseEntity.status(200).build() : ResponseEntity.status(400).build();
    }

    @Operation(
            summary = "Получение информации об остатках носков на складе по заданным параметрам",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Количество носков по заданным параметрам",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Ошибки в запросе"
                    )
            }, tags = "Api"
    )
    @GetMapping
    public ResponseEntity<?> getSocks(@RequestParam String color,
                                      @RequestParam String operation,
                                      @RequestParam Integer cottonPart) {

        if(color.length() < 2 || cottonPart < 0 || cottonPart > 100 || !OPERATIONS.contains(operation)) {
            return ResponseEntity.status(400).build();
        }
        return ResponseEntity.ok(socksService.getSocks(color, operation, cottonPart));
    }
}
