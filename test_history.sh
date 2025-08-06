#!/bin/bash

# 审批历史查询接口测试脚本

BASE_URL="http://localhost:8080"

echo "=== 审批历史查询接口测试 ==="
echo

# 1. 查询当前用户所在部门的审批历史记录
echo "1. 查询当前用户所在部门的审批历史记录"
response=$(curl -s -X GET "$BASE_URL/history/queryAll?pageNo=1&pageSize=10")

echo "响应: $response"
echo

# 2. 验证响应格式
echo "2. 验证响应格式"
if echo "$response" | grep -q '"code":0'; then
    echo "✓ 响应code为0"
else
    echo "✗ 响应code不是0"
fi

if echo "$response" | grep -q '"content"'; then
    echo "✓ 包含content字段"
else
    echo "✗ 缺少content字段"
fi

if echo "$response" | grep -q '"currentPage"'; then
    echo "✓ 包含currentPage字段"
else
    echo "✗ 缺少currentPage字段"
fi

if echo "$response" | grep -q '"pageSize"'; then
    echo "✓ 包含pageSize字段"
else
    echo "✗ 缺少pageSize字段"
fi

if echo "$response" | grep -q '"totalPages"'; then
    echo "✓ 包含totalPages字段"
else
    echo "✗ 缺少totalPages字段"
fi

if echo "$response" | grep -q '"totalElements"'; then
    echo "✓ 包含totalElements字段"
else
    echo "✗ 缺少totalElements字段"
fi

echo
echo "=== 测试完成 ===" 