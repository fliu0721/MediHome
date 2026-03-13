<template>
  <div class="login-container">
    <el-card class="login-box">
      <h2 class="title">家庭药品管理系统</h2>
      
      <el-tabs v-model="activeTab">
        <el-tab-pane label="登录" name="login">
          <el-form :model="loginForm" :rules="rules" ref="loginFormRef">
            <el-form-item prop="phone">
              <el-input 
                v-model="loginForm.phone" 
                placeholder="手机号"
                prefix-icon="Iphone"
              />
            </el-form-item>
            <el-form-item prop="password">
              <el-input 
                v-model="loginForm.password" 
                type="password" 
                placeholder="密码"
                prefix-icon="Lock"
                @keyup.enter="handleLogin"
              />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleLogin" :loading="loading" style="width: 100%">
                登录
              </el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        
        <el-tab-pane label="注册" name="register">
          <el-form :model="registerForm" :rules="registerRules" ref="registerFormRef">
            <el-form-item prop="phone">
              <el-input 
                v-model="registerForm.phone" 
                placeholder="手机号"
                prefix-icon="Iphone"
              />
            </el-form-item>
            <el-form-item prop="verifyCode">
              <el-input 
                v-model="registerForm.verifyCode" 
                placeholder="验证码"
                prefix-icon="Message"
              >
                <template #append>
                  <el-button @click="sendCode" :disabled="codeDisabled">
                    {{ codeText }}
                  </el-button>
                </template>
              </el-input>
            </el-form-item>
            <el-form-item prop="password">
              <el-input 
                v-model="registerForm.password" 
                type="password" 
                placeholder="密码"
                prefix-icon="Lock"
              />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleRegister" :loading="loading" style="width: 100%">
                注册
              </el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
      </el-tabs>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { login, register, getVerifyCode } from '@/api/user'
import { useUserStore } from '@/store/user'

const router = useRouter()
const userStore = useUserStore()
const activeTab = ref('login')
const loading = ref(false)

// 登录表单
const loginForm = reactive({
  phone: '',
  password: ''
})

// 注册表单
const registerForm = reactive({
  phone: '',
  password: '',
  verifyCode: ''
})

const rules = {
  phone: [{ required: true, message: '请输入手机号', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

const registerRules = {
  phone: [{ required: true, message: '请输入手机号', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
  verifyCode: [{ required: true, message: '请输入验证码', trigger: 'blur' }]
}

const loginFormRef = ref()
const registerFormRef = ref()

// 登录
const handleLogin = async () => {
  await loginFormRef.value.validate()
  loading.value = true
  try {
    const res = await login(loginForm)
    userStore.setToken(res.token)
    userStore.setUserInfo(res.user)
    ElMessage.success('登录成功')
    router.push('/')
  } finally {
    loading.value = false
  }
}

// 注册
const handleRegister = async () => {
  await registerFormRef.value.validate()
  loading.value = true
  try {
    const res = await register(registerForm)
    userStore.setToken(res.token)
    userStore.setUserInfo(res.user)
    ElMessage.success('注册成功')
    router.push('/')
  } finally {
    loading.value = false
  }
}

// 发送验证码
const codeDisabled = ref(false)
const codeText = ref('获取验证码')
let countdown = 60

const sendCode = async () => {
  if (!registerForm.phone) {
    ElMessage.warning('请输入手机号')
    return
  }
  await getVerifyCode(registerForm.phone)
  ElMessage.success('验证码已发送（开发环境固定为123456）')
  codeDisabled.value = true
  codeText.value = `${countdown}s`
  const timer = setInterval(() => {
    countdown--
    codeText.value = `${countdown}s`
    if (countdown <= 0) {
      clearInterval(timer)
      codeDisabled.value = false
      codeText.value = '获取验证码'
      countdown = 60
    }
  }, 1000)
}
</script>

<style scoped>
.login-container {
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.login-box {
  width: 400px;
  padding: 20px;
}

.title {
  text-align: center;
  margin-bottom: 30px;
  color: #333;
}
</style>
