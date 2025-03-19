package org.example.langchain4jdemo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.langchain4jdemo.controller.request.CreatePetRequest;
import org.example.langchain4jdemo.entity.Pet;
import org.example.langchain4jdemo.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 宠物控制器
 */
@RestController
@RequestMapping("/pets")
@Tag(name = "宠物管理", description = "宠物相关接口")
public class PetController {

    @Autowired
    private PetService petService;

    /**
     * 获取宠物列表
     *
     * @param page 页码
     * @param size 每页大小
     * @return 宠物列表
     */
    @GetMapping
    @Operation(summary = "获取宠物列表", description = "分页获取宠物列表")
    public ResponseEntity<?> getPets(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer page,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer size) {
        return ResponseEntity.ok(petService.getPets(page, size));
    }

    /**
     * 获取宠物详情
     *
     * @param id 宠物ID
     * @return 宠物详情
     */
    @GetMapping("/{id}")
    @Operation(summary = "获取宠物详情", description = "根据ID获取宠物详情")
    public ResponseEntity<?> getPetById(
            @Parameter(description = "宠物ID") @PathVariable Integer id) {
        Pet pet = petService.getPetById(id);
        if (pet != null) {
            return ResponseEntity.ok(pet);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("宠物不存在");
        }
    }

    /**
     * 创建宠物
     *
     * @param request 宠物请求
     * @return 宠物ID
     */
    @PostMapping
    @Operation(summary = "创建宠物", description = "创建新的宠物")
    public ResponseEntity<?> createPet(@RequestBody CreatePetRequest request) {
        Pet pet = new Pet();
        pet.setName(request.getName());
        pet.setType(request.getType());
        pet.setBreed(request.getBreed());
        pet.setGender(request.getGender());
        pet.setColor(request.getColor());
        pet.setBirthDate(request.getBirthDate());
        pet.setDescription(request.getDescription());
        pet.setLostTime(request.getLostTime());
        pet.setLostLocation(request.getLostLocation());
        pet.setContact(request.getContact());
        pet.setOwnerId(request.getOwnerId());
        pet.setStatus("LOST");

        Integer petId = petService.addPet(pet);
        
        Map<String, Object> response = new HashMap<>();
        response.put("id", petId);
        response.put("message", "宠物创建成功");
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 更新宠物信息
     *
     * @param id   宠物ID
     * @param pet  宠物信息
     * @return 更新结果
     */
    @PutMapping("/{id}")
    @Operation(summary = "更新宠物信息", description = "更新宠物的基本信息")
    public ResponseEntity<?> updatePet(
            @Parameter(description = "宠物ID") @PathVariable Integer id,
            @RequestBody Pet pet) {
        pet.setId(id);
        boolean success = petService.updatePet(pet);
        if (success) {
            return ResponseEntity.ok("宠物信息更新成功");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("宠物不存在");
        }
    }

    /**
     * 删除宠物
     *
     * @param id 宠物ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除宠物", description = "删除指定ID的宠物")
    public ResponseEntity<?> deletePet(
            @Parameter(description = "宠物ID") @PathVariable Integer id) {
        boolean success = petService.deletePet(id);
        if (success) {
            return ResponseEntity.ok("宠物删除成功");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("宠物不存在");
        }
    }

    /**
     * 标记宠物为已找回
     *
     * @param id 宠物ID
     * @return 标记结果
     */
    @PutMapping("/{id}/found")
    @Operation(summary = "标记宠物为已找回", description = "将宠物状态更新为已找回")
    public ResponseEntity<?> markPetAsFound(
            @Parameter(description = "宠物ID") @PathVariable Integer id) {
        boolean success = petService.markPetAsFound(id);
        if (success) {
            return ResponseEntity.ok("宠物已标记为找回");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("宠物不存在");
        }
    }

    /**
     * 搜索宠物
     *
     * @param keyword 关键词
     * @param page    页码
     * @param size    每页大小
     * @return 宠物列表
     */
    @GetMapping("/search")
    @Operation(summary = "搜索宠物", description = "根据关键词搜索宠物")
    public ResponseEntity<?> searchPets(
            @Parameter(description = "搜索关键词") @RequestParam String keyword,
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer page,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer size) {
        return ResponseEntity.ok(petService.searchPets(keyword, page, size));
    }

    /**
     * 筛选宠物
     *
     * @param type      宠物类型
     * @param status    状态
     * @param startTime 走失开始时间
     * @param endTime   走失结束时间
     * @param page      页码
     * @param size      每页大小
     * @return 宠物列表
     */
    @GetMapping("/filter")
    @Operation(summary = "筛选宠物", description = "根据条件筛选宠物")
    public ResponseEntity<?> filterPets(
            @Parameter(description = "宠物类型") @RequestParam(required = false) String type,
            @Parameter(description = "状态") @RequestParam(required = false) String status,
            @Parameter(description = "走失开始时间") 
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "走失结束时间") 
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime,
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer page,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer size) {
        return ResponseEntity.ok(petService.filterPets(type, status, startTime, endTime, page, size));
    }

    /**
     * 获取用户的宠物列表
     *
     * @param ownerId 主人ID
     * @return 宠物列表
     */
    @GetMapping("/owner/{ownerId}")
    @Operation(summary = "获取用户的宠物列表", description = "获取指定用户的所有宠物")
    public ResponseEntity<List<Pet>> getPetsByOwnerId(
            @Parameter(description = "主人ID") @PathVariable Integer ownerId) {
        return ResponseEntity.ok(petService.getPetsByOwnerId(ownerId));
    }
}
