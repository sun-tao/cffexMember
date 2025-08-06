#!/bin/bash

# 会员入会申请系统认证测试脚本

BASE_URL="http://localhost:8080"

echo "=== 会员入会申请系统认证测试 ==="
echo

# 1. 测试未登录状态下的API访问
echo "1. 测试未登录状态下的API访问"
response=$(curl -s -w "%{http_code}" -X GET "$BASE_URL/api/auth/current")
http_code="${response: -3}"
if [ "$http_code" = "401" ]; then
    echo "✓ 未登录状态下正确返回401"
else
    echo "✗ 未登录状态下返回了错误的HTTP状态码: $http_code"
fi
echo

# 2. 测试会员登录
echo "2. 测试会员登录"
response=$(curl -s -X POST "$BASE_URL/api/auth/login" \
    -H "Content-Type: application/x-www-form-urlencoded" \
    -d "username=member001&password=123456" \
    -c cookies.txt)

if echo "$response" | grep -q "登录成功"; then
    echo "✓ 会员登录成功"
else
    echo "✗ 会员登录失败: $response"
fi
echo

# 3. 测试获取当前用户信息
echo "3. 测试获取当前用户信息"
response=$(curl -s -X GET "$BASE_URL/api/auth/current" -b cookies.txt)
if echo "$response" | grep -q "member001"; then
    echo "✓ 成功获取当前用户信息"
else
    echo "✗ 获取用户信息失败: $response"
fi
echo

# 4. 测试会员提交申请
echo "4. 测试会员提交申请"
response=$(curl -s -X POST "$BASE_URL/api/applications/submit" \
    -H "Content-Type: application/json" \
    -b cookies.txt \
    -d '{
        "formData": "{\"company\":\"测试公司\",\"contact\":\"13800138000\"}"
    }')

if echo "$response" | grep -q "操作成功"; then
    echo "✓ 会员成功提交申请"
else
    echo "✗ 会员提交申请失败: $response"
fi
echo

# 5. 测试会员尝试审批（应该失败）
echo "5. 测试会员尝试审批（应该失败）"
response=$(curl -s -X POST "$BASE_URL/api/approvals/approve" \
    -H "Content-Type: application/json" \
    -b cookies.txt \
    -d '{
        "applicationId": 1,
        "taskId": 1,
        "operationType": "APPROVE",
        "comments": "测试审批"
    }')

if echo "$response" | grep -q "会员不能进行审批操作"; then
    echo "✓ 正确阻止会员进行审批操作"
else
    echo "✗ 会员审批权限检查失败: $response"
fi
echo

# 6. 登出
echo "6. 测试登出"
response=$(curl -s -X POST "$BASE_URL/api/auth/logout" -b cookies.txt)
if echo "$response" | grep -q "登出成功"; then
    echo "✓ 登出成功"
else
    echo "✗ 登出失败: $response"
fi
echo

# 7. 测试审批人员登录
echo "7. 测试审批人员登录"
response=$(curl -s -X POST "$BASE_URL/api/auth/login" \
    -H "Content-Type: application/x-www-form-urlencoded" \
    -d "username=trade_junior001&password=123456" \
    -c cookies2.txt)

if echo "$response" | grep -q "登录成功"; then
    echo "✓ 审批人员登录成功"
else
    echo "✗ 审批人员登录失败: $response"
fi
echo

# 8. 测试审批人员尝试提交申请（应该失败）
echo "8. 测试审批人员尝试提交申请（应该失败）"
response=$(curl -s -X POST "$BASE_URL/api/applications/submit" \
    -H "Content-Type: application/json" \
    -b cookies2.txt \
    -d '{
        "formData": "{\"company\":\"测试公司\",\"contact\":\"13800138000\"}"
    }')

if echo "$response" | grep -q "只有会员可以提交申请"; then
    echo "✓ 正确阻止审批人员提交申请"
else
    echo "✗ 审批人员提交申请权限检查失败: $response"
fi
echo

# 清理临时文件
rm -f cookies.txt cookies2.txt

echo "=== 测试完成 ===" 