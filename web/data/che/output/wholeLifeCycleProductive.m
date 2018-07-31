clear;
close all

costCol = 1:10;
effortCol = 11:15;
pureNumWholeWeek0 = xlsread('analyze.xls');
pureNumWholeWeek0(end+1,:) = 0;
normalizedPureNumberWholeWeek0 = mapminmax(pureNumWholeWeek0',0,1)';
sumWeeklyEffort0 = sum(normalizedPureNumberWholeWeek0(:,effortCol),2);
sumWeeklyCost0 = sum(normalizedPureNumberWholeWeek0(:,costCol),2);
productivity0 = sumWeeklyEffort0./sumWeeklyCost0;

figure('name','whole lifecycle')
hold on;
plot(productivity0,'-x')
%plot(normalizedPureNumberWholeWeek0(:,12))

pureNumWholeWeek4 = xlsread('analyzeV4.xls');
pureNumWholeWeek4(end+1,:) = 0;
normalizedPureNumberWholeWeek4 = mapminmax(pureNumWholeWeek4',0,1)';
sumWeeklyEffort4 = sum(normalizedPureNumberWholeWeek4(:,effortCol),2);
sumWeeklyCost4 = sum(normalizedPureNumberWholeWeek4(:,costCol),2);
%productivity4 = sumWeeklyEffort4./sumWeeklyCost4;
authors4 = normalizedPureNumberWholeWeek4(:,16);
productivity4 = sumWeeklyCost4./authors4;
traditionalProductivity4 = normalizedPureNumberWholeWeek4(:,12)./authors4;
%pd4 = productivity4.*traditionalProductivity4;

pureNumWholeWeek5 = xlsread('analyzeV5.xls');
pureNumWholeWeek5(end+1,:) = 0;
normalizedPureNumberWholeWeek5 = mapminmax(pureNumWholeWeek5',0,1)';
sumWeeklyEffort5 = sum(normalizedPureNumberWholeWeek5(:,effortCol),2);
sumWeeklyCost5 = sum(normalizedPureNumberWholeWeek5(:,costCol),2);
%productivity5 = sumWeeklyEffort5./sumWeeklyCost5;
authors5 = normalizedPureNumberWholeWeek5(:,16);
productivity5 = sumWeeklyCost5./authors5;
traditionalProductivity5 = normalizedPureNumberWholeWeek5(:,12)./authors5;
%pd5 = productivity5.*traditionalProductivity5;

pureNumWholeWeek5e1 = xlsread('analyzeV5.1.xls');
pureNumWholeWeek5e1(end+1,:) = 0;
normalizedPureNumberWholeWeek5e1 = mapminmax(pureNumWholeWeek5e1',0,1)';
sumWeeklyEffort5e1 = sum(normalizedPureNumberWholeWeek5e1(:,effortCol),2);
sumWeeklyCost5e1 = sum(normalizedPureNumberWholeWeek5e1(:,costCol),2);
%productivity5e1 = sumWeeklyEffort5e1./sumWeeklyCost5e1;
authors5e1 = normalizedPureNumberWholeWeek5e1(:,16);
productivity5e1 = sumWeeklyCost5e1./authors5e1;
traditionalProductivity5e1 = normalizedPureNumberWholeWeek5e1(:,12)./authors5e1;
%pd5e1 = productivity5e1.*traditionalProductivity5e1;

pureNumWholeWeek6 = xlsread('analyzeV6.xls');
pureNumWholeWeek6(end+1,:) = 0;
normalizedPureNumberWholeWeek6 = mapminmax(pureNumWholeWeek6',0,1)';
sumWeeklyEffort6 = sum(normalizedPureNumberWholeWeek6(:,effortCol),2);
sumWeeklyCost6 = sum(normalizedPureNumberWholeWeek6(:,costCol),2);
%productivity6 = sumWeeklyEffort6./sumWeeklyCost6;
authors6 = normalizedPureNumberWholeWeek6(:,16);
productivity6 = sumWeeklyCost6./authors6;
traditionalProductivity6 = normalizedPureNumberWholeWeek6(:,12)./authors6; 
%pd6 = productivity6.*traditionalProductivity6;

figure('name','three versions by week')
clf;
hold on;
plot(productivity4,'-o')
plot(productivity5,'-s')
plot(productivity6,'-x')
hold off;

figure('name','v4 normal and traditional')
clf;
hold on;
plot(productivity4,'-o')
%plot(normalizedPureNumberWholeWeek4(:,12),'-x')
plot(traditionalProductivity4,'-x')
plot(productivity4./traditionalProductivity4/100,'-v')
%plot(pd4,'-s')
hold off;

figure('name','v5 normal and traditional')
clf;
hold on;
plot(productivity5,'-o')
%plot(normalizedPureNumberWholeWeek5(:,12),'-x')
plot(traditionalProductivity5,'-x')
%plot(pd5,'-s')
plot(productivity5./traditionalProductivity5/20,'-v')
hold off;

figure('name','v6 normal and traditional')
clf;
hold on;
plot(productivity6,'-o')
%plot(normalizedPureNumberWholeWeek6(:,12),'-x')
plot(traditionalProductivity6,'-x')
%plot(pd6,'-s')
plot(productivity6./traditionalProductivity6/100,'-v')
hold off;

close all;

figure('name','output')
clf;
hold on;
plot(productivity5./traditionalProductivity5-1,'-x')
xlabel('time(week)')
ylabel('Non-trad/Trad')
hold off;