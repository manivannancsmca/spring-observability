# 🚀 Step-by-Step Execution Guide

## Step 1: Start the Observability Infrastructure

In the terminal directory containing `docker-compose.yml` and `tempo.yaml`, run:

```bash
docker-compose up -d
```

Verify that all containers are healthy and running:

```bash
docker ps
```

---

## Step 2: Build and Run Spring Boot Applications

### Start Order Service

Open a terminal in the `order-service` directory:

```bash
mvn clean package -DskipTests
mvn spring-boot:run
```

### Start Payment Service

Open a second terminal in the `payment-service` directory:

```bash
mvn clean package -DskipTests
mvn spring-boot:run
```

---

## Step 3: Generate Test Traces

Traces are only sent to Tempo when API endpoints are called.

Make 3–5 requests to your endpoints.

### Call Order Service

```bash
curl -X GET http://localhost:8081/orders
```

### Call Payment Service

```bash
curl -X GET http://localhost:8082/payments
```

---

## 🔍 Step 4: Verify Registration in Tempo

### Option A: Check Registered Services via Browser API

Open the following URL in your web browser:

```text
http://localhost:3200/api/v2/search/tag/resource.service.name/values
```

### Expected Result

```json
["order-service", "payment-service"]
```

### Option B: Check Raw Service Tags Endpoint

Open:

```text
http://localhost:3200/api/v2/search/tags
```

Verify that `"name": "resource"` contains:

```text
service.name
```

---

## 📊 Step 5: Configure Grafana to View Traces

Open Grafana in your browser:

```text
http://localhost:3000
```

### Grafana Login

| Setting | Value |
|---|---|
| Username | `admin` |
| Password | `admin` |

### Add Tempo Data Source

1. Go to **Connections > Data Sources**.
2. Click **Add data source**.
3. Select **Tempo**.
4. Set the URL to:

```text
http://tempo:3200
```

5. Expand **Node Graph** settings.
6. Toggle **Enable Node Graph** to **On**.
7. Click **Save & Test**.
8. You should see a green success banner.

---

## 🔎 Search and Query Traces in Grafana

1. Navigate to **Explore** using the compass icon on the left toolbar.
2. Select **Tempo** from the top-left data source dropdown.
3. Switch the query mode to **TraceQL**.
4. Enter the following query:

```traceql
{ resource.service.name = "order-service" }
```

5. Click **Run Query**.

Grafana should display the matching traces along with the trace timeline visualizer.

---

## ✅ Verification Checklist

- [ ] Docker Compose infrastructure is running.
- [ ] Tempo container is healthy.
- [ ] Order Service is running on port `8081`.
- [ ] Payment Service is running on port `8082`.
- [ ] Test API requests have been executed.
- [ ] `order-service` appears in Tempo service names.
- [ ] `payment-service` appears in Tempo service names.
- [ ] Grafana is accessible on port `3000`.
- [ ] Tempo is configured as a Grafana data source.
- [ ] Node Graph is enabled.
- [ ] TraceQL query returns `order-service` traces.

# 📊 Prometheus & Grafana Dashboard Setup

## Step 3: Verify Prometheus Scraping Targets

1. Open `http://localhost:9090/targets` in your web browser.
2. You should see both `order-service` and `payment-service` listed with a green **UP** status.

---

## Step 4: Add Prometheus Data Source in Grafana

1. Open Grafana at `http://localhost:3000`.
2. Log in with:
   - **Username:** `admin`
   - **Password:** `admin`
3. Go to **Connections** > **Data Sources** > **Add data source**.
4. Select **Prometheus**.
5. Set the **Prometheus server URL** to:

```text
http://prometheus:9090
```

6. Click **Save & Test**.
7. A green success message should appear confirming the connection.

---

## Step 5: Import Out-of-the-Box Spring Boot Dashboard

Instead of building charts manually from scratch, you can import a community dashboard built for Spring Boot.

### Import Dashboard

1. In Grafana, click the **`+` (Plus)** icon or open the **Dashboards** menu on the left.
2. Click **Import**.
3. In the **Import via grafana.com** text box, enter one of the following dashboard IDs:
   - **`4701`** — Spring Boot dashboard
   - **`11378`** — JVM Micrometer dashboard
4. Click **Load**.
5. Select your **Prometheus** data source from the bottom dropdown.
6. Click **Import**.

---

## 📊 What You Will See in Grafana

Once the dashboard is imported, it provides real-time visibility across your Spring Boot services.

### JVM Metrics

- **JVM Memory / Heap Usage** for both services.
- **CPU Usage**.
- **Live Thread Counts**.

### HTTP Metrics

- **HTTP Request Throughput**.
- **HTTP Request Latency**.
- Request metrics for endpoints such as:
  - `/orders`
  - `/payments`

### 🔄 Service Switcher

The dashboard provides a **Service Switcher Dropdown** using the `$application` variable at the top of the dashboard.

You can use it to switch between:

```text
order-service
payment-service
```

This allows you to monitor each Spring Boot service without creating a separate dashboard for every application.

---

## ✅ Verification Checklist

- [ ] Prometheus is accessible at `http://localhost:9090`.
- [ ] `order-service` appears as **UP** in Prometheus targets.
- [ ] `payment-service` appears as **UP** in Prometheus targets.
- [ ] Grafana is accessible at `http://localhost:3000`.
- [ ] Prometheus is configured as a Grafana data source.
- [ ] **Save & Test** succeeds.
- [ ] Spring Boot dashboard is imported.
- [ ] JVM memory metrics are visible.
- [ ] CPU and thread metrics are visible.
- [ ] HTTP throughput and latency metrics are visible.
- [ ] `$application` service switcher works for `order-service` and `payment-service`.
